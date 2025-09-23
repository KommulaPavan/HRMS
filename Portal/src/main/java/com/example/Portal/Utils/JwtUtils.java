package com.example.Portal.Utils;

import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    private static final String BASE64_SECRET =
            "q1tFv7WJxj2e3iK5Y8LwZrN+uPbE9Vq6s4hCk0FjHxGtLd2Z0VxYj9wQe7Hs1RzA";

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(Base64.getDecoder().decode(BASE64_SECRET));

    private final long expMilliSeconds = 24 * 60 * 60 * 1000; // 24h

    public String createToken(Employee employee) {
        List<String> rolesList = employee.getRole().stream()
                .map(Role::getName) // e.g. "ADMIN" or "ROLE_ADMIN" – keep consistent
                .toList();

        return Jwts.builder()
                //.setSubject(employee.getEmail())
                .setSubject(employee.getEmployeeId())

                .claim("employeeId", employee.getEmployeeId())
                .claim("email",employee.getEmail())
                .claim("roles", rolesList)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expMilliSeconds))
                .signWith(secretKey)
                .compact();
    }

    // NEW: validate by token only
    public boolean validateToken(String token) {
        try {
            getAllClaims(token); // parses & verifies signature/exp
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /** Get employeeId (prefer claim, fallback to subject). */
    public String getEmployeeId(String token) {
        Claims claims = getAllClaims(token);
        String empId = claims.get("employeeId", String.class);
        return (empId != null && !empId.isBlank()) ? empId : claims.getSubject();
    }


    // NEW: get email (subject)
    public String getEmail(String token) {
        Claims claims = getAllClaims(token);
        return claims.get("email", String.class);
    }

  /*  public String employeeId(String token){
        return getAllClaims(token).getSubject();
    }*/

    // kept for backward-compat (but prefer validateToken(token))
    public boolean isValidToken(String userName, String token) {
        try {
            return userName.equals(getUserNameFromToken(token)) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserNameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        return getAllClaims(token).get("roles", List.class);
    }

    public boolean isTokenExpired(String token) {
        return getAllClaims(token).getExpiration().before(new Date());
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody();
    }
}
