package com.nguyenklinh.shopapp.configurations;

import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        try {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(reques -> {
                        reques.requestMatchers("**").permitAll();
                    });

            return http.build();
        } catch (Exception e) {
            throw new MyException(ErrorCode.CAN_NOT_CREATE_SECURITY_FILTER_CHAIN);
        }
    }
}
