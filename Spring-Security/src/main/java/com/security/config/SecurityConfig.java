package com.security.config;

import com.security.security.JwtAuthenticationEntryPoint;
import com.security.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configurable
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //spring security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
//        httpSecurity.authorizeHttpRequests(request -> {
//            request.requestMatchers("/api/route2").permitAll();
//            request.requestMatchers("/user/**").permitAll();
//            request.requestMatchers(HttpMethod.POST, "/product/**").permitAll();
//            request.anyRequest().authenticated();
//
//        });


        //request chaining

//        httpSecurity.authorizeHttpRequests(request -> {
//            request.requestMatchers("/api/route2").permitAll()
//                    .requestMatchers("/user/**").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/product/**").permitAll()
//                    .anyRequest().authenticated();
//        });

        httpSecurity.authorizeHttpRequests(request -> {
            request.requestMatchers("/api/route1", "/api/route2").hasRole("ADMIN")
                    .requestMatchers("/api/route3", "/api/route4").hasRole("GUEST")
                    .requestMatchers(HttpMethod.POST, "/auth/generate-token").permitAll()
                    .requestMatchers("/auth/**").authenticated();
        });

//        httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }

    //    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//                .username("rashi")
//                .password("rashi").roles("ADMIN").build();
//
//        UserDetails user2 = User.withDefaultPasswordEncoder()
//                .username("rashi2")
//                .password("rashi2").roles("ADMIN").build();
//
//
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2);
//        return inMemoryUserDetailsManager;
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
