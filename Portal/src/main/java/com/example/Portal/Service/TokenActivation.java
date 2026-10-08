package com.example.Portal.Service;


import com.example.Portal.Entity.ActivationToken;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.ActivationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenActivation {

    @Autowired
    private ActivationTokenRepository tokenRepository;

    public ActivationToken createToken(Employee employee) {
        ActivationToken token = new ActivationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setEmployee(employee);
        token.setCreatedAt(LocalDateTime.now());
        //token.setExpiresAt(LocalDateTime.now().plusHours(24)); // ⬅ Restored (24h validity)
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));// --Restored (10 min validity)

        return tokenRepository.save(token); // ⬅ Save & return token
    }

    public ActivationToken validateToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));
    }

    @Transactional
    public void deleteToken(Employee employee) {
        tokenRepository.deleteByEmployee(employee);
    }
}
