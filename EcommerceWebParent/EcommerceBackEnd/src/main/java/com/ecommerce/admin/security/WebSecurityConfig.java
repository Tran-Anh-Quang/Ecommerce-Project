package com.ecommerce.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        return new EcommerceUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    SecurityFilterChain configureHttpSecurity(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").hasAuthority("Admin")
                        .requestMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")
                        .requestMatchers("/products/**").hasAnyAuthority("Admin", "Editor", "Sales Person", "Shipper")
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .permitAll())

                .logout(LogoutConfigurer::permitAll)

                .rememberMe(rem -> rem
                    .key("AbcDefgHijKlmnOpqrs_1234567890")
                    .tokenValiditySeconds(7 * 24 * 60 * 60));

        return http.build();
    }

    @Bean
    WebSecurityCustomizer configureWebSecurity() throws Exception {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }

}
