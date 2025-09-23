// src/main/java/com/example/Portal/Utils/JwtTokenAuthentication.java
package com.example.Portal.Utils;

import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.EmployeeRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component  // 👈 add this
public class JwtTokenAuthentication extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;
    private final EmployeeRepository employeeRepository;

    // 👇 constructor injection (remove @Autowired fields)
    public JwtTokenAuthentication(JwtUtils jwtUtil, EmployeeRepository employeeRepository) {
        this.jwtUtil = jwtUtil;
        this.employeeRepository = employeeRepository;
    }
    // PUBLIC_PATTERNS: REMOVE "/api/auth/me"
    private static final List<String> PUBLIC_PATTERNS = List.of(
            "/api/auth/login",
            "/api/auth/activate/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/home"
    );


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return "OPTIONS".equalsIgnoreCase(request.getMethod())
                || path.equals("/api/auth/login")
                || path.startsWith("/api/auth/activate");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                if (jwtUtil.validateToken(token)) {
                   /* String email = jwtUtil.getEmail(token);
                    Employee emp = employeeRepository.findByEmail(email).orElse(null);*/
                    String employeeId=jwtUtil.getEmployeeId(token);
                    Employee emp=employeeRepository.findByEmployeeId(employeeId).orElse(null);
                    if (emp != null) {
                        List<String> roles = jwtUtil.getRolesFromToken(token);
                        var authorities = roles.stream()
                                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                                .distinct()
                                .map(SimpleGrantedAuthority::new)
                                .toList();

                        var auth = new UsernamePasswordAuthenticationToken(emp, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        request.setAttribute("user", emp); // optional
                    }
                }
            } catch (Exception ignored) {}
        }
        chain.doFilter(request, response);
    }
}
