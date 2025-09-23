package com.example.Portal.Config;

import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.Role;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createUserRole(EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        return args -> {
            Role adminrole=roleRepository.findByName("ROLE_ADMIN").orElseGet(()-> {
                Role role = new Role();
                role.setName("ROLE_ADMIN");
                roleRepository.save(role);
                System.out.println("Role is Created");
                return role;
                });

            // Create ROLE_USER if not exists
           Role Hrrole= roleRepository.findByName("ROLE_HR").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_HR");
                roleRepository.save(role);
                System.out.println("Role Created: ROLE_USER");
                return role;
            });

            roleRepository.findByName("ROLE_EMPLOYEE").orElseGet(()->{
                Role role=new Role();
                role.setName("ROLE_EMPLOYEE");
                roleRepository.save(role);
                System.out.println("Role is Created");
                return role;

            });


            employeeRepository.findByEmail("pavan@gmail.com").ifPresentOrElse(user -> {
                // Update existing user
                user.setAccountStatus("ACTIVE"); // ✅ ensure account is active
                user.setRole(Set.of(adminrole)); // assign ROLE_ADMIN if missing
                employeeRepository.save(user);
                System.out.println("Existing Admin updated with ACTIVE status and ROLE_ADMIN");
            }, () -> {
                // Create new admin
                Employee user = new Employee();
                user.setEmployeeId("EMP001");
                user.setName("Admin User");
                user.setEmail("pavan@gmail.com");
                user.setPassword(passwordEncoder.encode("Pa123"));
                user.setRole(Set.of(adminrole)); // assign ROLE_ADMIN
                //user.setEmployeeId("AD-101");
                user.setAccountStatus("ACTIVE"); // ✅ set active
                employeeRepository.save(user);
                System.out.println("Admin Created Successfully with ACTIVE status");
            });



         /*   employeeRepository.findByEmail("ravindraadmin@gmail.com").orElseGet(() -> {
                Employee user = new Employee();
                user.setEmployeeId("EMP002");
                user.setName("Admin User");
                user.setEmail("ravindraadmin@gmail.com");
                user.setPassword(passwordEncoder.encode("ravindraadmin@2329"));
                user.setRole(Set.of(adminrole));
                user.setAccountStatus("ACTIVE");  // 👈 add this
                employeeRepository.save(user);
                System.out.println("Admin Created Successfully...");
                return user;
            });*/
            employeeRepository.findByEmail("priya@gmail.com").ifPresentOrElse(user -> {
                // Update existing user
                user.setAccountStatus("ACTIVE"); // ✅ ensure account is active
                user.setRole(Set.of(Hrrole)); // assign ROLE_ADMIN if missing
                employeeRepository.save(user);
                System.out.println("Existing Admin updated with ACTIVE status and ROLE_Hr");
            }, () -> {
                // Create new admin
                Employee user = new Employee();
                user.setEmployeeId("HR001");
                user.setName("Priya");
                user.setEmail("priya@gmail.com");
                user.setPassword(passwordEncoder.encode("Priya123"));
                user.setRole(Set.of(Hrrole)); // assign ROLE_ADMIN
                user.setAccountStatus("ACTIVE"); // ✅ set active
                employeeRepository.save(user);
                System.out.println("Admin Created Successfully with ACTIVE status");
            });

            Employee admin = employeeRepository.findByEmail("pavan@gmail.com").orElse(null);
            if (admin != null) {
                System.out.println("Admin in DB:");
                System.out.println("Email: " + admin.getEmail());
                System.out.println("Roles: " + admin.getRole().stream().map(Role::getName).toList());
                System.out.println("Admin Status: " + admin.getAccountStatus());
                System.out.println("AdminId:"+ admin.getEmployeeId());
            }

            Employee Hr = employeeRepository.findByEmail("priya@gmail.com").orElse(null);
            if (Hr != null) {
                System.out.println("Admin in DB:");
                System.out.println("Email: " + Hr.getEmail());
                System.out.println("Roles: " + Hr.getRole().stream().map(Role::getName).toList());
                System.out.println("Admin Status: " + Hr.getAccountStatus());
                System.out.println("AdminId:"+ Hr.getEmployeeId());
            }


        };
    }



    }



