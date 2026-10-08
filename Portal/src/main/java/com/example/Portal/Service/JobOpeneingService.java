package com.example.Portal.Service;


import com.example.Portal.Dto.JobRequest;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.JobOpening;
import com.example.Portal.Entity.JobStatus;
import com.example.Portal.Entity.JobType;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobOpeneingService {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    Logger logger= LoggerFactory.getLogger(JobOpeneingService.class);

    public JobOpening createJob(JobRequest jobRequest){


        JobOpening jobOpening=new JobOpening();
        jobOpening.setTitle(jobRequest.getTitle());
        jobOpening.setDepartment(jobRequest.getDepartment());
        jobOpening.setType(JobType.valueOf(jobRequest.getType().toUpperCase()));
        jobOpening.setLocation(jobRequest.getLocation());
        jobOpening.setOpenings(jobRequest.getOpenings());
        Double salaryMin = jobRequest.getSalaryMin();
        Double salaryMax = jobRequest.getSalaryMax();

        if (salaryMin == null || salaryMax == null) {
            throw new IllegalArgumentException("Salary min and max are required");
        }

        if (salaryMin > salaryMax) {
            throw new IllegalArgumentException("Salary min cannot be greater than salary max");
        }

        jobOpening.setSalaryMin(salaryMin);
        jobOpening.setSalaryMax(salaryMax);
        jobOpening.setSalaryRange(
                salaryMin.intValue() + "-" + salaryMax.intValue()
        );

        logger.info("FINAL salaryRange before save = {}", jobOpening.getSalaryRange());

        jobOpening.setStatus(JobStatus.valueOf(jobRequest.getStatus().toUpperCase()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null ||
                !auth.isAuthenticated() ||
                auth instanceof AnonymousAuthenticationToken) {

            throw new RuntimeException("Unauthorized: Login required");
        }

        // auth.getName() == employeeId (NOT email)
       // Employee emp=new Employee();
        String employeeId = auth.getName();


        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        logger.error("Employee id not found");

        jobOpening.setEmployee(employee);

        jobRepository.save(jobOpening);
        logger.info("Job is created successfully");
        return jobOpening;
    }


    public List<JobRequest> getAllJobs(){
        return jobRepository.findAll().stream().map(JobRequest::forEntity).collect(Collectors.toList());
    }

    public void deleteJob(Long id){
        jobRepository.deleteById(id);
    }
}
