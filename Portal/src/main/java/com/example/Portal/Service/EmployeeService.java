package com.example.Portal.Service;



import com.example.Portal.Dto.EmployeeRequest;
import com.example.Portal.Dto.EmployeeResponse;
import com.example.Portal.Dto.EmployeeReviewDto;
import com.example.Portal.Dto.MailMessageDAO;
import com.example.Portal.Entity.ActivationToken;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Role;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    com.example.Portal.Service.MailService mailService;
    @Autowired
    com.example.Portal.Service.TokenActivation tokenActivation;
    @Autowired
    SequenceService sequenceService;

  /*  public String CreateEmployee(EmployeeRequest employeeRequest){
    if(employeeRepository.findByEmail(employeeRequest.getEmail()).isPresent()){
        throw new RuntimeException("Email is aleady register");

    }

    Role role=roleRepository.findByName(employeeRequest.getRole())
            .orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName(employeeRequest.getRole());
                roleRepository.save(newRole);
                System.out.println("Role auto-created: " + employeeRequest.getRole());
                return newRole;
            });

        Employee employee=new Employee();
        employee.setName(employeeRequest.getName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setRole(Set.of(role));
        employee.setEmployeeId(employeeRequest.getEmployeeId());
        employee.setDesignation(employeeRequest.getDesignation());
        employee.setDepartment(employeeRequest.getDepartment());
        employeeRepository.save(employee);

        return "Employee Register";
    }*/
  private String normalizeRole(String input) {
      if (input == null || input.isBlank()) return "ROLE_EMPLOYEE";
      String r = input.trim().toUpperCase(Locale.ROOT);
      return r.startsWith("ROLE_") ? r : "ROLE_" + r;
  }

    public String EmployeeMailSender(EmployeeRequest req) {

        String roleName = normalizeRole(req.getRole());
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role nr = new Role();
                    nr.setName(roleName);        // << correct role name
                    return roleRepository.save(nr);
                });
        String nextCode=sequenceService.assignNextId();
        // Create employee
        Employee employee = new Employee();
        employee.setEmployeeId(nextCode);
        employee.setName(req.getName());
        employee.setEmail(req.getEmail());
        employee.setRole(Set.of(role));
        employee.setDepartment(req.getDepartment());
        employee.setDesignation(req.getDesignation());

        employee.setAccountStatus("pending");

        Employee saved = employeeRepository.save(employee);

        // Generate activation token
        ActivationToken token = tokenActivation.createToken(saved);

        // Prepare email
        MailMessageDAO mailMessageDAO = new MailMessageDAO();
        mailMessageDAO.setId(UUID.randomUUID().toString());
        mailMessageDAO.setTo(saved.getEmail());
        mailMessageDAO.setSubject("Activate Your Account");
        mailMessageDAO.setBody(
                "Hello " + saved.getName() + ",\n\n" +
                        "Please activate your account by clicking the link below:\n" +
                        "http://localhost:5173/activate/" + token.getToken() + "\n\n" +
                        "This link will expire in 24 hours.\n\n" +
                        "Thank you!"
        );
        mailMessageDAO.setLink("http://localhost:5173/activate/" + token.getToken());
        mailMessageDAO.setActivation(false);
        mailMessageDAO.setCreatedAt(LocalDateTime.now());

        // Send email
        mailService.sendLinkToMail(mailMessageDAO);

        return "Employee saved and activation email sent.";
    }


    public List<EmployeeResponse> getALlEmployee(){
        return employeeRepository.findAll().stream().map(EmployeeResponse::fromEntity).collect(Collectors.toList());
    }

    public EmployeeReviewDto getEmployee(String employeeId){

        Employee employee = employeeRepository
                .findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeReviewDto dto = new EmployeeReviewDto();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setName(employee.getName());
        dto.setDepartment(employee.getDepartment());

        return dto;
    }

}
