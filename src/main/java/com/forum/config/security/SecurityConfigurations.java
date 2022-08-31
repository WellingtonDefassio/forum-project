package com.forum.config.security;

import com.forum.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfigurations {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    public SecurityConfigurations(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth -> auth.mvcMatchers(HttpMethod.GET, "/topicos**").permitAll()
                        .mvcMatchers(HttpMethod.POST, "/auth**").permitAll()
                        .anyRequest().authenticated())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilterAt(new AutenticacaoViaTokerFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();


    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

