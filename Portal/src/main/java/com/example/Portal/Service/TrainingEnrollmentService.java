package com.example.Portal.Service;

import com.example.Portal.Dto.EnrollResponse;
import com.example.Portal.Dto.RosterItemDto;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Training;
import com.example.Portal.Entity.TrainingEnrollment;
import com.example.Portal.Entity.TrainingStatus;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.TrainingEnrollmentRepository;
import com.example.Portal.Repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrainingEnrollmentService {

    private final TrainingRepository trainingRepository;
    private final EmployeeRepository employeeRepository;
    private final TrainingEnrollmentRepository enrollmentRepository;

    public TrainingEnrollmentService(TrainingRepository trainingRepository,
                                     EmployeeRepository employeeRepository,
                                     TrainingEnrollmentRepository enrollmentRepository) {
        this.trainingRepository = trainingRepository;
        this.employeeRepository = employeeRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    /** Create training and generate public UID if missing. */
    public Training create(Training t) {
        if (t.getTrainingId() == null || t.getTrainingId().isBlank()) {
            t.setTrainingId(UUID.randomUUID().toString());
        }
        return trainingRepository.save(t);
    }

    /** Enroll multiple employees into a training by public training UID. */
    @Transactional
    public EnrollResponse enroll(String trainingUid, List<String> employeeIds) {
        // 1) resolve training by public UID
        final Training training = trainingRepository.findByTrainingId(trainingUid)
                .orElseThrow(() -> new EntityNotFoundException("Training not found: " + trainingUid));
        final Long trainingPk = training.getId();

        // 2) parse employee ids -> Long, collect bad formats
        final List<String> skippedBadFormat = new ArrayList<>();
        final List<Long> targetIds = (employeeIds == null ? List.<String>of() : employeeIds).stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    try { return Long.valueOf(s); }
                    catch (Exception ex) { skippedBadFormat.add(s); return null; }
                })
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // 3) load employees
        final List<Employee> foundEmployees = targetIds.isEmpty()
                ? List.of()
                : employeeRepository.findAllById(targetIds);

        final Map<Long, Employee> empById = foundEmployees.stream()
                .collect(Collectors.toMap(Employee::getId, e -> e, (a,b)->a, LinkedHashMap::new));

        final Set<Long> foundIds = new LinkedHashSet<>(empById.keySet());

        // 4) not found in DB
        final List<String> skippedNotFound = targetIds.stream()
                .filter(id -> !foundIds.contains(id))
                .map(String::valueOf)
                .toList();

        // 5) already enrolled
        final List<Long> alreadyEnrolled = foundIds.isEmpty()
                ? List.of()
                : enrollmentRepository.findAlreadyEnrolledIds(Math.toIntExact(trainingPk), foundIds);

        // 6) to enroll now
        final List<Long> toEnrollIds = foundIds.stream()
                .filter(id -> !alreadyEnrolled.contains(id))
                .toList();

        // 7) persist new enrollments
        for (Long empId : toEnrollIds) {
            final TrainingEnrollment te = new TrainingEnrollment();
            te.setId(UUID.randomUUID().toString());
            te.setTraining(training);
            te.setEmployee(empById.get(empId));
            te.setStatus(TrainingStatus.ENROLLED);
            te.setProgress(0);
            te.setCreatedAt(OffsetDateTime.now());
            enrollmentRepository.save(te);
        }

        // 8) API-friendly response (string ids)
        return new EnrollResponse(
                training.getTrainingId(),
                toEnrollIds.stream().map(String::valueOf).toList(),
                alreadyEnrolled.stream().map(String::valueOf).toList(),
                Stream.concat(skippedBadFormat.stream(), skippedNotFound.stream()).toList()
        );
    }

    /** Update an attendee’s status/progress. Uses public training UID + employee id. */
    @Transactional
    public void updateProgress(String trainingUid, String employeeId,
                               TrainingStatus status, Integer progress) {
        if (trainingUid == null || employeeId == null) {
            throw new IllegalArgumentException("trainingId and employeeId are required");
        }
        final Long empId;
        try {
            empId = Long.valueOf(employeeId);
        } catch (NumberFormatException nfe) {
            throw new EntityNotFoundException("Employee not found (bad id): " + employeeId);
        }

        TrainingEnrollment te = enrollmentRepository
                .findByTraining_TrainingIdAndEmployee_Id(trainingUid, empId)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found"));

        te.setStatus(status != null ? status : TrainingStatus.ENROLLED);
        if (progress != null) {
            int p = Math.max(0, Math.min(100, progress));
            te.setProgress(p);
        }
        te.setUpdatedAt(OffsetDateTime.now());
        enrollmentRepository.save(te);
    }

    /** Roster by public training UID (returns DTO list). */
    @Transactional
    public List<RosterItemDto> roster(String trainingUid) {
        if (trainingUid == null || trainingUid.isBlank()) {
            throw new IllegalArgumentException("trainingId is required");
        }
        // Ensure training exists (by UID)
        trainingRepository.findByTrainingId(trainingUid)
                .orElseThrow(() -> new EntityNotFoundException("Training not found: " + trainingUid));

        return enrollmentRepository.findAllByTrainingUidWithEmployee(trainingUid)
                .stream()
                .map(te -> new RosterItemDto(
                        String.valueOf(te.getEmployee().getId()),
                        te.getStatus().name().toLowerCase(),
                        Optional.ofNullable(te.getProgress()).orElse(0),
                        nvl(te.getEmployee().getName()),
                        nvl(te.getEmployee().getEmail()),
                        nvl(te.getEmployee().getDepartment())
                ))
                .toList();
    }

    private static String nvl(String s) { return s == null ? "" : s; }
}
