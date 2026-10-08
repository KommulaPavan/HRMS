package com.example.Portal.Client;

import com.example.Portal.Dto.EmployeeRecord;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class EmployeeClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<EmployeeRecord> getAllEmployess() {

        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());

        return restTemplate.exchange(
                "http://localhost:8080/api/employees",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<EmployeeRecord>>() {}
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
