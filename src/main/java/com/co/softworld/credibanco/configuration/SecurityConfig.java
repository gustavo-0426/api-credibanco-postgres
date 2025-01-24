package com.co.softworld.credibanco.configuration;

import com.co.softworld.credibanco.exception.InvalidCustomerException;
import com.co.softworld.credibanco.repository.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${api.version}")
    private String apiVersion;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        final String urlCard = "/" + apiVersion + "/card/**";
        final String urlProduct = "/" + apiVersion + "/product/**";
        final String urlTransaction = "/" + apiVersion + "/transaction/**";
        final String urlCustomer = "/" + apiVersion + "/customer/**";

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, urlCard).hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, urlCard).hasRole("admin")
                        .requestMatchers(HttpMethod.GET, urlCard).hasAnyRole("admin", "test")

                        .requestMatchers(HttpMethod.POST, urlProduct).hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, urlProduct).hasRole("admin")
                        .requestMatchers(HttpMethod.GET, urlProduct).hasAnyRole("admin", "test")

                        .requestMatchers(HttpMethod.POST, urlTransaction).hasRole("admin")
                        .requestMatchers(HttpMethod.GET, urlTransaction).hasAnyRole("admin", "test")

                        .requestMatchers(HttpMethod.POST, urlCustomer).hasRole("admin")
                        .requestMatchers(HttpMethod.GET, urlCustomer).hasAnyRole("admin", "test")

                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(ICustomerRepository customerRepository) {

        return username -> customerRepository.findByUsername(username)
                .map(customer ->
                        User.builder()
                                .username(customer.getUsername())
                                .password(customer.getPassword())
                                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(customer.getRoles()))
                                .build()
                ).orElseThrow(() -> new InvalidCustomerException("Not found customer with username: " + username));
    }
}
