package com.example.mysecurity.config;

import com.example.mysecurity.config.filter.CustomUsernamePasswordAuthenticationFilter;
import com.example.mysecurity.config.provider.CustomDaoAuthenticationProvider;
import com.example.mysecurity.config.provider.CustomUserDetailsService;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.addFilterBefore(
                new CustomUsernamePasswordAuthenticationFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class
        );
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/assets/**", "/login", "/error").permitAll()
                                .requestMatchers("/user").hasRole("USER")
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login"));

        SecurityFilterChain chain = http.build();
        List<Filter> filters = chain.getFilters();
        for (Filter filter : filters) {
            System.out.println(filter.getClass());
        }

        return chain;
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider(CustomUserDetailsService userDetailsService) {

        CustomDaoAuthenticationProvider authenticationProvider = new CustomDaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setPreAuthenticationChecks(new CustomPreAuthenticationChecks());
//        authenticationProvider.setPostAuthenticationChecks(new CustomPostAuthenticationChecks());

        return authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
