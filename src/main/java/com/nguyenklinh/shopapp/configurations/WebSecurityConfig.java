package com.nguyenklinh.shopapp.configurations;

import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.filters.JwtTokenFilter;
import com.nguyenklinh.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String prefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        try {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests(requests -> {
                        requests
                                .requestMatchers(
                                        String.format("%s/users/register",prefix),
                                        String.format("%s/users/login",prefix)
                                ).permitAll()
                                .requestMatchers(PUT,
                                        String.format("%s/orders/**",prefix)).hasRole(Role.ADMIN)
                                .requestMatchers(POST,
                                        String.format("%s/orders/**",prefix)).hasRole(Role.USER)
                                .requestMatchers(GET,
                                        String.format("%s/orders/**",prefix)).hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(DELETE,
                                        String.format("%s/orders/**",prefix)).hasRole(Role.ADMIN)
                                .anyRequest().authenticated();
                    });

            return http.build();
        } catch (Exception e) {
            throw new MyException(ErrorCode.CAN_NOT_CREATE_SECURITY_FILTER_CHAIN);
        }
    }
}
