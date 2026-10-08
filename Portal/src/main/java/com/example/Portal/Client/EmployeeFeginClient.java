package com.example.Portal.Client;

import com.example.Portal.Dto.EmployeeReviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="Portal" ,url = "http://localhost:8080")
public interface EmployeeFeginClient {
    @GetMapping("/api/employees/{employeeId}")
    EmployeeReviewDto getEmployee(@PathVariable("employeeId") String employeeId);
}
