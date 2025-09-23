package com.example.Portal.Repository;

import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Long> {
    List<Leave> findByEmployee(Employee employee);
    Optional<Leave> findById(Long id);
    Optional<Leave> findByLeaveId(String leaveId);
}
