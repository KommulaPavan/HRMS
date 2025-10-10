package com.example.Portal.Repository;

import com.example.Portal.Entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Integer> {
    // Lookup by public UID (String)
    Optional<Training> findByTrainingId(String trainingId);
}
