package com.managementsystem.accountservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Oauth2ClientConfig {

    @Bean(name = "client")
    ReactiveClientRegistrationRepository getRepository(
            @Value("${token_uri}") String tokenUri,
            @Value("${client-id}") String clientId,
            @Value("${client_credential}") String clientSecret
    ) {
                ClientRegistration registration = ClientRegistration
                .withRegistrationId("server")
                .tokenUri(tokenUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

    @Bean(name = "server-only")
    @LoadBalanced
    WebClient.Builder webClient(@Qualifier(value = "client") ReactiveClientRegistrationRepository clientRegistrations) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                    clientRegistrations, new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
        oauth.setDefaultClientRegistrationId("server");
        return WebClient.builder()
                .filter(oauth);
    }
}
