package com.example.Portal.Repository;


import com.example.Portal.Entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile,Long> {


    Optional<EmployeeProfile> findByEmployeeId(String employeeId);
}
