package com.ElectronicStoreSpringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails normal = User.builder()
                .username("Javed")
                .password(passwordEncoder.encode("javed"))
                .roles("NORMAL")
                .build();

        UserDetails admin = User.builder()
                .username("Jawaid")
                .password(passwordEncoder.encode("jawaid"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(normal, admin);
    }
}
