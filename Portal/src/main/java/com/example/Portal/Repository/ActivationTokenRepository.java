package com.example.Portal.Repository;


import com.example.Portal.Entity.ActivationToken;
import com.example.Portal.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken,Long> {

    Optional<ActivationToken> findByToken(String token);

    void deleteByEmployee(Employee employee);

}
