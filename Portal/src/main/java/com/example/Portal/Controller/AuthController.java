package com.example.Portal.Controller;


import com.example.Portal.Dto.AuthRequest;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    EmployeeRepository userrepo;
    @PostMapping("/auth")
    public String generateToken(@RequestBody AuthRequest authRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())
        );

        Employee employee = userrepo.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));


        return  jwtUtils.createToken(employee);

    }

}
