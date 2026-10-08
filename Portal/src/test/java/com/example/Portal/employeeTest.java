package com.example.Portal;

import com.example.Portal.Dto.EmployeeRequest;
import com.example.Portal.Entity.ActivationToken;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Role;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.RoleRepository;
import com.example.Portal.Service.EmployeeService;
import com.example.Portal.Service.MailService;
import com.example.Portal.Service.SequenceService;
import com.example.Portal.Service.TokenActivation;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class employeeTest {
    @Mock
    RoleRepository roleRepository;
    @Mock
    SequenceService sequenceService;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    TokenActivation tokenActivation;
@InjectMocks
EmployeeService employeeService;
    @Mock
    MailService mailService;   // ✅ MISSING DEPENDENCY ADDED


    @Test
    void testEmployeeSender_NewRoleCreation(){

    EmployeeRequest employeeRequest=new EmployeeRequest();
//        employeeRequest.setEmployeeId("101");
        employeeRequest.setName("sai");
        employeeRequest.setEmail("pa@gmail.com");
        employeeRequest.setRole("Tester");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenAnswer(i ->i.getArgument(0));
        when(sequenceService.assignNextId()).thenReturn("Emp001");
        Employee employee=new Employee();
        employee.setName("sai");
        employee.setEmail("pa@gmail.com");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        ActivationToken activationToken=new ActivationToken();
        activationToken.setToken("xyz");
        when(tokenActivation.createToken(any(Employee.class))).thenReturn(activationToken);
        String res=employeeService.EmployeeMailSender(employeeRequest);
        assertEquals("Employee saved and activation email sent.", res);
        verify(roleRepository).save(any(Role.class));
        verify(mailService).sendLinkToMail(any());

    }
}