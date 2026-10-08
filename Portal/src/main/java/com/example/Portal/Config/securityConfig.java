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

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenAuthentication jwtFilter) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
////                .authorizeHttpRequests(auth -> auth
////
////                        // Public
////                        .requestMatchers(
////                                "/api/auth/**",
////                                "/swagger-ui/**",
////                                "/v3/api-docs/**"
////                        ).permitAll()
////
////                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
////                        //.requestMatchers("/api/admin/jobs").permitAll()
////
////
////                        // Admin
////                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
////                        //.anyRequest().authenticated()
////                        .requestMatchers("/api/admin/jobs").hasRole("ADMIN")
////
////                        // HR
////                        .requestMatchers("/api/hr/**").hasAnyRole("HR","ADMIN")
////
////                        // Employee
////                        .requestMatchers("/api/employees/**").authenticated()
////                        .requestMatchers("/api/attendance/**").authenticated()
////                        .requestMatchers("/api/leaves/**").authenticated()
////                        .requestMatchers("/api/trainings/**").authenticated()
////
////                        .anyRequest().authenticated()
////                )
//
//                .authorizeHttpRequests(auth -> auth
//                        // Public endpoints
//                        .requestMatchers("/api/auth/login", "/api/auth/activate/**","/api/announcements","/api/announcements/**").permitAll()
//                        .requestMatchers( "/swagger-ui.html",
//                                "/swagger-ui/**",
//                                "/v3/api-docs",
//                                "/v3/api-docs/**",
//                                "/v3/api-docs.yaml",
//                                "/swagger-resources",
//                                "/swagger-resources/**",
//                                "/webjars/**").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//
//
//                        // Me endpoints
//                        .requestMatchers("/api/auth/me").authenticated()
//                        .requestMatchers("/api/employees/me").authenticated()
//                        .requestMatchers("/api/leaves/me").authenticated()
//                        // ----- ATTENDANCE -----
//                        // Punching allowed to signed-in employees (or broader roles if you like)
//                        .requestMatchers(HttpMethod.POST, "/api/attendance/check-in", "/api/attendance/check-out")
//                        .hasAnyRole("EMPLOYEE","ADMIN","HR","MANAGER")
//                        // "me" endpoints: need to be logged in
//                        .requestMatchers("/api/attendance/me/**").authenticated()
//                        // Admin/HR/Manager queries for other employees:
//                        .requestMatchers("/api/attendance/history", "/api/attendance/month")
//                        .hasAnyRole("ADMIN","HR","MANAGER")
//
//
//                        // Leaves
//                        .requestMatchers(HttpMethod.POST, "/api/leaves").hasAnyRole("EMPLOYEE","HR","MANAGER","ADMIN")
//                        .requestMatchers("/api/leaves/**").authenticated()
//
//                        .requestMatchers("/api/hr/leaves","/api/hr/leaves/*/approve","/api/hr/trainings","/api/hr/trainings/roster","/api/hr/trainings/{trainingId}/enrollments"
//                        ,"/api/hr/trainings/{trainingId}/roster","/api/hr/trainings/enroll","/api/hr/trainings/{id}/enroll").hasAnyRole("ADMIN","HR")
//
//                        // Employees (your code had singular vs plural; align with your controllers)
//                        .requestMatchers("/api/employee/**", "/api/employees/**").authenticated()
//                        .requestMatchers("/api/trainings","/api/trainings/roster").authenticated()
//
//                        // Admin-only
//                        .requestMatchers("/api/employees/invite", "/api/outbox/emails", "/api/employees","/admin/**",
//                                "/api/leave-calendar", "/api/leave-calendar/**","/api/attendance/reports","api/employees/preview-next-id","/api/announcements","/api/admin/trainings"
//                        ,"/api/admin/trainings/{id}/enroll","/api/admin/trainings/{trainingId}/roster","/api/admin/trainings/roster","/api/admin/trainings/{trainingId}/enrollments"
//                        ,"/api/admin/trainings/{trainingId}/roster","/api/admin/trainings/enroll","/api/admin/trainings/{id}/enroll","/api/admin/jobs","/api/admin/jobs/*","/api/employee/Generate").hasRole("ADMIN")
//
//                        // HR-only section (keep if you really need)
//                        .requestMatchers("/hr/**","/api/hr/employees").hasRole("HR")
//
//                        .requestMatchers("/api/attendance/check-in","/api/attendance/check-out","/api/employees/**").hasRole("EMPLOYEE")
//                        .requestMatchers("/api/attendance/me/**").authenticated()
//
//                        // securityConfig: in authorizeHttpRequests(...)
//                        .requestMatchers("/api/trainings/**").authenticated()  // << covers list + roster path-style
//                        .requestMatchers("/api/hr/trainings/**").hasAnyRole("HR","ADMIN")
//                        .requestMatchers("/api/admin/trainings/**").hasRole("ADMIN")
//                        .requestMatchers("/api/employee/**").permitAll()
//                        .requestMatchers("/api/employees").permitAll()
//
//
//
//                        .anyRequest().authenticated()
//                )
//
//                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                               JwtTokenAuthentication jwtFilter) throws Exception {

    http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            .authorizeHttpRequests(auth -> auth

                    /* =========================
                       PUBLIC ENDPOINTS
                    ========================== */
                    .requestMatchers(
                            "/api/auth/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll()

                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                  //  .requestMatchers("/api/employees/**").permitAll()

                    /* =========================
                       PAYROLL (ADMIN ONLY)
                    ========================== */
                    .requestMatchers(HttpMethod.POST, "/api/employee/Generate").hasRole("ADMIN")

                    .requestMatchers("/api/payroll/**").hasRole("ADMIN")
                    .requestMatchers("/api/performance").hasRole("ADMIN")
                    .requestMatchers("/api/performance","/api/performance/**").hasRole("ADMIN")

                    /* =========================
                       EMPLOYEE SELF
                    ========================== */
                    .requestMatchers(
                            "/api/auth/me",
                            "/api/employees/me",
                            "/api/leaves/me"
                    ).authenticated()

                    .requestMatchers("/api/attendance/me/**").authenticated()

                    /* =========================
                       ATTENDANCE
                    ========================== */
                    .requestMatchers(
                            HttpMethod.POST,
                            "/api/attendance/check-in",
                            "/api/attendance/check-out"
                    ).hasAnyRole("EMPLOYEE", "ADMIN", "HR", "MANAGER")

                    .requestMatchers(
                            "/api/attendance/history",
                            "/api/attendance/month",
                            "/api/attendance/reports"
                    ).hasAnyRole("ADMIN", "HR", "MANAGER")

                    /* =========================
                       LEAVES
                    ========================== */
                    .requestMatchers(HttpMethod.POST, "/api/leaves")
                    .hasAnyRole("EMPLOYEE", "HR", "MANAGER", "ADMIN")

                    .requestMatchers("/api/leaves/**").authenticated()

                    /* =========================
                       EMPLOYEES (ADMIN / HR)
                    ========================== */
                    .requestMatchers("/api/employees","/api/employees/**").hasAnyRole("ADMIN", "HR")

                    /* =========================
                       TRAININGS
                    ========================== */
                    .requestMatchers("/api/trainings/**").authenticated()
                    .requestMatchers("/api/hr/trainings/**").hasAnyRole("HR", "ADMIN")
                    .requestMatchers("/api/admin/trainings/**").hasRole("ADMIN")

                    /* =========================
                       ADMIN
                    ========================== */
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")

                    /* =========================
                       FALLBACK
                    ========================== */
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
