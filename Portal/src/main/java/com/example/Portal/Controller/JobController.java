package com.example.Portal.Controller;


import com.example.Portal.Dto.JobRequest;
import com.example.Portal.Service.JobOpeneingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    JobOpeneingService jobOpeneingService;

//    @PostMapping("/api/admin/jobs")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getJobAlert(@RequestBody JobRequest jobRequest){
//
//        return ResponseEntity.ok(jobOpeneingService.createJob(jobRequest));
//    }

    @PostMapping("/api/admin/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getJobAlert(@RequestBody JobRequest jobRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        return ResponseEntity.ok(jobOpeneingService.createJob(jobRequest));
    }
    @GetMapping("/api/admin/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public List<JobRequest> getalljob(){
        return jobOpeneingService.getAllJobs();
    }

    @DeleteMapping("/api/admin/jobs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void getdeleted(@PathVariable Long id){
        jobOpeneingService.deleteJob(id);
    }



}
