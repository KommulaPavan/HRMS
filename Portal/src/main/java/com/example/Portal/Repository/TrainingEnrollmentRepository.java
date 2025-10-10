package com.example.Portal.Repository;

import com.example.Portal.Entity.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, String> {

    // For “already enrolled” check (use numeric PK internally)
    @Query("""
      select te.employee.id
      from TrainingEnrollment te
      where te.training.id = :trainingPk and te.employee.id in :empIds
    """)
    List<Long> findAlreadyEnrolledIds(@Param("trainingPk") int trainingPk,
                                      @Param("empIds") Collection<Long> empIds);

    // Roster (by public UID), fetch-join employee to avoid N+1
    @Query("""
      select te
      from TrainingEnrollment te
        join fetch te.employee e
      where te.training.trainingId = :trainingUid
      order by e.name asc
    """)
    List<TrainingEnrollment> findAllByTrainingUidWithEmployee(@Param("trainingUid") String trainingUid);

    // Single enrollment (by public UID + employee id)
    Optional<TrainingEnrollment> findByTraining_TrainingIdAndEmployee_Id(String trainingId, Long employeeId);
}
