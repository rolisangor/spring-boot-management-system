package com.management.system.authservice.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    @Value(value = "${client.client-secret-ui}")
    private String clientSecretUi;

    @Value(value = "${client.client-secret-server}")
    private String clientSecretServer;

    @Value(value = "${client.signing-key}")
    private String signingKey;

    @Value(value = "${client.access-token-expire}")
    private int accessTokenExpire;

    @Value(value = "${client.refresh-token-expire}")
    private int refreshTokenExpire;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public Oauth2Config(@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                        PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer())
                .pathMapping("/oauth/token" , "/api/auth/token")
                .pathMapping("/oauth/check_token" , "/api/auth/check_token")
                .pathMapping("/oauth/authorize" , "/api/auth/authorize");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }

    // TODO: save clients in database
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("ui")
                .secret(passwordEncoder.encode(clientSecretUi))
                .resourceIds("apis")
                .scopes("ui")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(accessTokenExpire)
                .refreshTokenValiditySeconds(refreshTokenExpire)

                .and()
                .withClient("server")
                .secret(passwordEncoder.encode(clientSecretServer))
                .resourceIds("apis")
                .scopes("server")
                .authorizedGrantTypes("client_credentials")
                .accessTokenValiditySeconds(accessTokenExpire)
                .refreshTokenValiditySeconds(refreshTokenExpire);
    }
}
