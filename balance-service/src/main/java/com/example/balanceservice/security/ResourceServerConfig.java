package com.example.balanceservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${resources_id}")
    private String resourceId;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/account").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId);
    }
}
