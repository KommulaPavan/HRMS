package com.example.Portal.Repository;


import com.example.Portal.Entity.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<JobOpening,Long> {

    Optional<JobOpening> findById(Long id);
}
