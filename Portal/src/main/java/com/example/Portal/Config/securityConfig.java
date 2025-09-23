package com.example.Portal.Config;

import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Utils.JwtTokenAuthentication;
import com.example.Portal.Utils.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

public class securityConfig {

    private final EmployeeRepository employeeRepository;

    public securityConfig(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Bean
    JwtTokenAuthentication jwtTokenAuthentication(JwtUtils jwtUtils, EmployeeRepository employeeRepository) {
        return new JwtTokenAuthentication(jwtUtils, employeeRepository);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> employeeRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenAuthentication jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/login", "/api/auth/activate/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Me endpoints
                        .requestMatchers("/api/auth/me").authenticated()
                        .requestMatchers("/api/employees/me").authenticated()
                        .requestMatchers("/api/leaves/me").authenticated()

                        // Leaves
                        .requestMatchers(HttpMethod.POST, "/api/leaves").hasAnyRole("EMPLOYEE","HR","MANAGER","ADMIN")
                        .requestMatchers("/api/leaves/**").authenticated()

                        .requestMatchers("/api/**").hasAnyRole("ADMIN","HR")

                        // Employees (your code had singular vs plural; align with your controllers)
                        .requestMatchers("/api/employee/**", "/api/employees/**").authenticated()

                        // Admin-only
                        .requestMatchers("/api/employees/invite", "/api/outbox/emails", "/api/employees","/admin/**",
                                "/api/leave-calendar", "/api/leave-calendar/**").hasRole("ADMIN")

                        // HR-only section (keep if you really need)
                        .requestMatchers("/hr/**","/api/hr/employees").hasRole("HR")

                        .requestMatchers("/api/attendance/check-in","/api/attendance/check-out","/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers("/api/attendance/me/**").authenticated()

                        .anyRequest().authenticated()
                )

                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173"); // frontend
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(userDetailsService);
        daoAuthProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(List.of(daoAuthProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

                    //    .requestMatchers("/employee/all","/employee/leave/history","/api/attendance/history","/api/leave-calendar").hasAnyRole("ADMIN", "HR", "EMPLOYEE")
                    //    .requestMatchers("/api/attendance/clock","/api/attendance/history").hasAnyRole("EMPLOYEE","HR")
