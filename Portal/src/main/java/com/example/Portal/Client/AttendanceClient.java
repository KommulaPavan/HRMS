package com.example.Portal.Client;

import com.example.Portal.Dto.AttadanceRecord;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Component
public class AttendanceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<AttadanceRecord> getAttendance(LocalDate from, LocalDate to) {

        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());

        return restTemplate.exchange(
                "http://localhost:8080/api/attendance/me/rangee?from={from}&to={to}",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<AttadanceRecord>>() {},
                from,
                to
        ).getBody();
    }

    private HttpHeaders buildHeaders() {
        Object credentials = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getCredentials();

        if (credentials == null) {
            throw new IllegalStateException("JWT token not found in SecurityContext");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(credentials.toString());
        return headers;
    }
}
