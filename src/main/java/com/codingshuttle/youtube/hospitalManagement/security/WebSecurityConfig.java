package com.codingshuttle.youtube.hospitalManagement.security;

import com.codingshuttle.youtube.hospitalManagement.entity.type.PermissionType;
import com.codingshuttle.youtube.hospitalManagement.entity.type.RoleType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.codingshuttle.youtube.hospitalManagement.entity.type.PermissionType.*;
import static com.codingshuttle.youtube.hospitalManagement.entity.type.RoleType.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        ///  All permissions
                        .requestMatchers(
                                "/api/v1/login", "/api/v1/signup", "/swagger-ui/**", "/v3/api-docs/**"
                        ).permitAll()

                        ///  All permissions
                        .requestMatchers("/public/**", "/auth/**").permitAll()

                       /// RolePermissionMapping based authorization
                        .requestMatchers(HttpMethod.DELETE, "/admin/**")
                            .hasAnyAuthority(APPOINTMENT_DELETE.name(),
                                USER_MANAGE.name())

                        /// only role based authorization
                        .requestMatchers("/admin/**").hasRole(ADMIN.name())

                        /// only role based authorization
                        .requestMatchers("/doctors/**").hasAnyRole(DOCTOR.name(), ADMIN.name())

                        ///  All route should be authenticated except permitAll
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2 -> oAuth2
                        .failureHandler((request, response, exception) -> {
                            log.error("OAuth2 error: {}", exception.getMessage());
                            handlerExceptionResolver.resolveException(request, response, null, exception);
                        })
                        .successHandler(oAuth2SuccessHandler)
                )
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
                            handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
                        }));

//                .formLogin();
        return httpSecurity.build();
    }

}
