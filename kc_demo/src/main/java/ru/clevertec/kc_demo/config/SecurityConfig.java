package ru.clevertec.kc_demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import ru.clevertec.kc_demo.service.util.KCRoleConverter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(new KCRoleConverter());

        return http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                                authorizeHttpRequests
//                                        .requestMatchers(HttpMethod.GET).permitAll()
                                        .requestMatchers(HttpMethod.GET).hasAnyRole("user", "admin")
                                        .requestMatchers(HttpMethod.POST).hasRole("admin")
                                        .requestMatchers(HttpMethod.PUT).hasRole("admin")
                                        .requestMatchers(HttpMethod.DELETE).hasRole("admin")
                                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                                        oauth2ResourceServer
                                                .jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter)))

                .build();
    }
}
