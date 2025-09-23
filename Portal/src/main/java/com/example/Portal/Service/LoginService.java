package com.example.Portal.Service;

import com.example.Portal.Dto.EmployeeRequest;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils; // Make sure you have a JWT utility

    /*public String loginEmployee(EmployeeRequest employeeRequest){
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeRequest.getEmail());
        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException("Email not found");
        }

        Employee employee = optionalEmployee.get();

        if (!"Active".equalsIgnoreCase(employee.getAccountStatus())) {
            throw new RuntimeException("Account not activated");
        }

        if (!passwordEncoder.matches(employeeRequest.getPassword(), employee.getPassword())) {
            throw new RuntimeException("Invalid password");
        }


        return jwtUtils.createToken(employee);
    }*/

    public String loginEmployee(EmployeeRequest employeeRequest){
       Optional<Employee> optionalEmployee=employeeRepository.findByEmployeeId(employeeRequest.getEmployeeId());
       if(optionalEmployee.isEmpty()){
           throw new RuntimeException("EmployeeId not found");
       }
               Employee employee=optionalEmployee.get();
        if (!"Active".equalsIgnoreCase(employee.getAccountStatus())) {
            throw new RuntimeException("Account not activated");
        }

        if (!passwordEncoder.matches(employeeRequest.getPassword(), employee.getPassword())) {
            throw new RuntimeException("Invalid password");
        }


        return jwtUtils.createToken(employee);

    }
}


