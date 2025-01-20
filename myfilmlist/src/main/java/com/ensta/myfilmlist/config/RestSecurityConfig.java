package com.ensta.myfilmlist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class RestSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/accounts/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                .antMatchers(HttpMethod.PUT, "/accounts/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .antMatchers(HttpMethod.POST, "/accounts/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .antMatchers(HttpMethod.DELETE, "/accounts/**").hasRole("SUPERADMIN")
                .anyRequest().denyAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
        return http.build();
    }





    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        // Définition des utilisateurs avec rôles
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user")) // Mot de passe encodé
                .roles("USER") // Rôle attribué
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin")) // Mot de passe encodé
                .roles("USER", "ADMIN") // Rôles attribués
                .build();

        UserDetails superadmin = User.withUsername("superadmin")
                .password(passwordEncoder.encode("superadmin")) // Mot de passe encodé
                .roles("USER", "ADMIN", "SUPERADMIN") // Rôles attribués
                .build();

        return new InMemoryUserDetailsManager(user, admin, superadmin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
