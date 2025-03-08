package com.ElectronicStoreSpringboot.config;

import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    //These are the hard coded users
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails normal = User.builder()
//                .username("Javed")
//                .password(passwordEncoder().encode("javed"))
//                .roles("NORMAL")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("Jawaid")
//                .password(passwordEncoder().encode("jawaid"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normal, admin);
//    }

    @Autowired
    private UserDetailsService userDetailsService;

//    private final String[] PUBLIC_URLS = {
//
//            "/swagger-ui/**",
//            "/webjars/**",
//            "/swagger-resources/**",
//            "/v3/api-docs",
//            "/v2/api-docs"
//    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        //authenticating users by retrieving their details from a UserDetailsService and
        //validate their credentials(username and password)
        //instance of daoAuthenticationProvider is used by spring security authentication mechanism.
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

   // @Autowired
    //private JwtAuthenticationEntryPoint authenticationEntryPoint;

        //   @Autowired
//   private JwtAuthenticationFilter authenticationFilter;

   //basic authentication
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
               // .authorizeHttpRequests(auth -> auth
                   //     .requestMatchers("/auth/login").permitAll()
                       // .requestMatchers(HttpMethod.POST, "/users").permitAll()
                      //  .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                      //  .requestMatchers(PUBLIC_URLS).permitAll()
                      //  .anyRequest().authenticated().notifyAll();

                        return http.build();

    }
}
