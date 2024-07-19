package com.bist.backendmodule.configurations;

import com.bist.backendmodule.security.JwtAuthenticationFilter;
import com.bist.backendmodule.security.JwtAuthenticationProvider;
import com.bist.backendmodule.services.CustomPermissionEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class configures the security settings for the application.
 * It enables web security, method-level security, and integrates custom permission evaluator.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final CustomPermissionEvaluator customPermissionEvaluator;

    /**
     * Constructor for SecurityConfiguration class.
     *
     * @param customPermissionEvaluator Custom permission evaluator used to handle custom permission logic.
     */
    public SecurityConfiguration(CustomPermissionEvaluator customPermissionEvaluator) {
        this.customPermissionEvaluator = customPermissionEvaluator;
    }

    /**
     * MethodSecurityExpressionHandler bean to integrate custom permission evaluator.
     *
     * @return MethodSecurityExpressionHandler with custom permission evaluator set.
     */
    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
        return expressionHandler;
    }

    /**
     * AuthenticationManager bean to manage authentication process.
     *
     * @param httpSecurity       HttpSecurity object to configure authentication manager.
     * @param passwordEncoder    Password encoder used to encode passwords.
     * @param userDetailsService User details service used to load user-specific data.
     * @return AuthenticationManager built with user details service and password encoder.
     * @throws Exception if an error occurs during the authentication manager build.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    /**
     * PasswordEncoder bean to encode passwords using BCrypt.
     *
     * @return PasswordEncoder that uses BCrypt for password encoding.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * JwtAuthenticationFilter bean to filter JWT tokens.
     *
     * @return JwtAuthenticationFilter instance.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * JwtAuthenticationProvider bean to provide authentication logic for JWT tokens.
     *
     * @return JwtAuthenticationProvider instance.
     */
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider();
    }

    /**
     * SecurityFilterChain bean to configure security settings.
     *
     * @param httpSecurity HttpSecurity object to configure security settings.
     * @return SecurityFilterChain configured with custom security settings.
     * @throws Exception if an error occurs during the security filter chain build.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // TODO: authenticated urls will be revised
                        //.requestMatchers("/user/**").authenticated()
                        .requestMatchers("/engine-rest/**").permitAll()
                        .anyRequest().permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(jwtAuthenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
