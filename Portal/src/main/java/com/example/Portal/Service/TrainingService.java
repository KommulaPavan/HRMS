package com.example.Portal.Service;

import com.example.Portal.Dto.TrainingRequestDto;
import com.example.Portal.Entity.Training;
import com.example.Portal.Repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /** CREATE and return created DTO (with trainingId) */
    public TrainingRequestDto assignTraining(TrainingRequestDto dto){
        Training t = new Training();
        t.setTrainingId(UUID.randomUUID().toString());
        dto.applyTo(t);
        trainingRepository.save(t);
        return new TrainingRequestDto(t);
    }

    /** LIST */
    public List<TrainingRequestDto> getTrainingDetails(){
        return trainingRepository.findAll()
                .stream()
                .map(TrainingRequestDto::new)
                .collect(Collectors.toList());
    }

    /** UPDATE (PATCH-style) */
    public TrainingRequestDto updateTraining(String trainingId, TrainingRequestDto patch) {
        Training t = trainingRepository.findByTrainingId(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found: " + trainingId));
        patch.applyTo(t);
        trainingRepository.save(t);
        return new TrainingRequestDto(t);
    }

    /** DELETE */
    public void deleteTraining(String trainingId) {
        Training t = trainingRepository.findByTrainingId(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found: " + trainingId));
        trainingRepository.delete(t);
    }
}
