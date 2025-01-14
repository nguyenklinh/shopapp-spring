package com.nguyenklinh.shopapp.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.filters.JwtTokenFilter;
import com.nguyenklinh.shopapp.models.Role;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                    .cors(cors->cors.configurationSource(corsConfigurationSource()))
                    .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests(requests -> {
                        requests
                                .requestMatchers(HttpMethod.GET,
                                        String.format("%s/products/**",prefix),
                                        String.format("%s/roles**", prefix),
                                        String.format("%s/categories**", prefix),
                                        String.format("%s/product-images/**",prefix),
                                        String.format("%s/orders/**", prefix)
                                ).permitAll()

                                .requestMatchers(POST,
                                        String.format("%s/users/register",prefix),
                                        String.format("%s/users/login",prefix)
                                        ).permitAll()

                                .anyRequest().authenticated();
                    })
                    .exceptionHandling(exception -> exception
                            .accessDeniedHandler((request, response, accessDeniedException) ->
                                    response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage()))
                            .authenticationEntryPoint((request, response, authException) -> {
                                response.setContentType("application/json");
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                                ApiResponse<?> apiResponse = ApiResponse.builder()
                                        .code(1006) // Mã lỗi cho UNAUTHENTICATED
                                        .message("Unauthorized: " + authException.getMessage())
                                        .success(false)
                                        .build();

                                response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
                            })
                    );

            return http.build();

        } catch (Exception e) {
            throw new MyException(ErrorCode.CAN_NOT_CREATE_SECURITY_FILTER_CHAIN);
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        //Cho phép tất cả các nguồn (origins) gửi yêu cầu đến server.

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        //Chỉ định các HTTP methods mà client được phép sử dụng

        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        //Chỉ định các HTTP methods mà client được phép sử dụng

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        //Áp dụng cấu hình CORS cho tất cả các endpoint của ứng dụng
        return source;
    }

}
