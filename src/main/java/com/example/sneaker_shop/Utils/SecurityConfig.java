package com.example.sneaker_shop.Utils;

import com.example.sneaker_shop.Services.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import
        org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import
        org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import
        org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    String[] pathArrayPermitAll = new String[] { "/css/**", "/js/**", "/", "/register", "/login", "/error", "/shoes", "/verify", "/photos/**", "/shoes/detail/**" };
    String[] pathArrayNew = new String[] { "/shoes/add",  "categories/add" };
    String[] pathArrayUpdate = new String[] { "/shoes/edit/**", "/categories/edit/**", "/users/edit/**" };
    String[] pathArrayDelete = new String[] { "/shoes/delete/**", "/categories/delete/**" };

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        return http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(pathArrayPermitAll).permitAll()
                        .requestMatchers(pathArrayUpdate).hasAnyAuthority("ADMIN")
                        .requestMatchers(pathArrayDelete).hasAnyAuthority("ADMIN")
                        .requestMatchers(pathArrayNew).hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(userDetailsService())
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/403"))
                .build();
    }
}

