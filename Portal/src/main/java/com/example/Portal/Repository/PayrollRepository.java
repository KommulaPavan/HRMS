package com.example.Portal.Repository;


import com.example.Portal.Entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByMonth(String month);

    Optional<Payroll> findByEmployeeEmployeeIdAndMonth(
            String employeeId,
            String month
    );

    boolean existsByEmployeeEmployeeIdAndMonth(
            String employeeId,
            String month
    );
}
