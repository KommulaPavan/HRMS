package com.example.Portal.Repository;

import com.example.Portal.Entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Reviews,Long> {

    List<Reviews> findByEmployeeId(String employeeId);
}
