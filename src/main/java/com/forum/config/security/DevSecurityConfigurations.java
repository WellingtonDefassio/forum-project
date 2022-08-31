package com.forum.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class DevSecurityConfigurations {


    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth -> auth.mvcMatchers("/topicos**").permitAll()).csrf().disable();
        return http.build();
    }


}

