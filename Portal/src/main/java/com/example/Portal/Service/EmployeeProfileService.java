package com.example.Portal.Service;

import com.example.Portal.Dto.EmployeeProfileDto;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.EmployeeProfile;
import com.example.Portal.Entity.Role;
import com.example.Portal.Repository.EmployeeProfileRepository;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeProfileService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeProfileRepository employeeProfileRepository;
    @Autowired
    RoleRepository roleRepository;
    // Service (Multipart version)
    @Transactional
    public String updateProfile(EmployeeProfileDto dto, MultipartFile file) {
        if (dto.getEmployeeId() == null || dto.getEmployeeId().isBlank()) {
            throw new IllegalArgumentException("employeeId is required");
        }

        // 1) Load a MANAGED Employee (not 'new Employee()')
        Employee emp = employeeRepository.findByEmployeeId(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee id not found"));

        // 2) Upsert the profile
        EmployeeProfile profile = employeeProfileRepository.findByEmployeeId(dto.getEmployeeId())
                .orElseGet(() -> {
                    EmployeeProfile p = new EmployeeProfile();
                    // IMPORTANT: wire the required association
                    p.setEmployee(emp);
                    // If you also keep a string column:
                    p.setEmployeeId(emp.getEmployeeId());
                    return p;
                });

        // If existing row somehow lacks the link, fix it
        if (profile.getEmployee() == null) {
            profile.setEmployee(emp);
        }

        // 3) Copy fields
        profile.setName(dto.getName());
        profile.setEmail(dto.getEmail() != null ? dto.getEmail() : emp.getEmail());
        profile.setDepartment(dto.getDepartment());
        profile.setPhone(dto.getPhone());
        profile.setAbout(dto.getAbout());
        profile.setLocation(dto.getLocation());

        // optional avatar file
        if (file != null && !file.isEmpty()) {
            if (file.getSize() > 200 * 1024) throw new RuntimeException("Avatar must be <= 200KB");
            String ext = org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename());
            String safeExt = (ext != null ? ext.toLowerCase() : "png");
            java.nio.file.Path dir = java.nio.file.Path.of("uploads", "avatars");
            try {
                java.nio.file.Files.createDirectories(dir);
                java.nio.file.Path dest = dir.resolve(profile.getEmployeeId() + "." + safeExt);
                java.nio.file.Files.copy(file.getInputStream(), dest, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                profile.setAvatarUrl("/files/avatars/" + profile.getEmployeeId() + "." + safeExt);
            } catch (Exception e) {
                throw new RuntimeException("Failed to store avatar", e);
            }
        } else if (dto.getAvatarUrl() != null && !dto.getAvatarUrl().isBlank()) {
            profile.setAvatarUrl(dto.getAvatarUrl());
        }

        employeeProfileRepository.save(profile);
        return "profile updated successfully";
    }

   /* public String updateProfile(EmployeeProfileDto employeeProfileDto, MultipartFile file) {
        Employee employee = employeeRepository.findByEmail(employeeProfileDto.getEmail()).orElseThrow(() -> new RuntimeException("Email is not found"));
        EmployeeProfile employeeProfile = employeeProfileRepository.findByEmployeeId(employeeProfileDto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Profile id is not found"));
        employeeProfile.setEmployeeId(employeeProfileDto.getEmployeeId());
        employeeProfile.setName(employeeProfileDto.getName());
        employeeProfile.setEmail(employeeProfileDto.getEmail());
        employeeProfile.setDepartment(employeeProfileDto.getDepartment());
        employeeProfile.setPhone(employeeProfileDto.getPhone());
        employeeProfile.setAbout(employeeProfileDto.getAbout());

        if (file != null && !file.isEmpty()) {
            if (file.getSize() > 200 * 1024) {
                throw new RuntimeException("Avatar must be <= 200KB");
            }
            String ext = org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename());
            String safeExt = (ext != null ? ext.toLowerCase() : "png");

            // Example: save to disk (could also be S3, DB as BLOB, etc.)
            try {
                Path dir = Path.of("uploads", "avatars");
                Files.createDirectories(dir);
                Path dest = dir.resolve(employeeProfile.getEmployeeId() + "." + safeExt);
                Files.copy(file.getInputStream(), dest, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // Save URL/path into DB
                String url = "/files/avatars/" + employeeProfile.getEmployeeId() + "." + safeExt;
                employeeProfile.setAvatarUrl(url);
            } catch (Exception e) {
                throw new RuntimeException("Failed to store avatar", e);
            }
        } else {
            // No file: keep the DTO-provided avatar URL (if any)
            if (employeeProfileDto.getAvatarUrl() != null && !employeeProfileDto.getAvatarUrl().isBlank()) {
                employeeProfile.setAvatarUrl(employeeProfileDto.getAvatarUrl());
            }
        }
        employeeProfile.setLocation(employeeProfileDto.getLocation());
        employeeProfileRepository.save(employeeProfile);
        return "profile updated sucessfully";
    }*/

    public EmployeeProfileDto updateProfile(String employeeId){
//        Role adminrole=roleRepository.findByName("ROLE_ADMIN").orElseGet(()-> {
//            Role role = new Role();
//            role.setName("ROLE_ADMIN");
//            roleRepository.save(role);

        Employee emp=employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(()-> new RuntimeException("Employee id not found"));
        EmployeeProfile employeeProfile=employeeProfileRepository.findByEmployeeId(employeeId).orElse(null);
        EmployeeProfileDto dto=new EmployeeProfileDto();
        dto.setEmployeeId(emp.getEmployeeId());
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        if (emp.getRole() != null && !emp.getRole().isEmpty()) {
            String firstRole = emp.getRole().iterator().next().getName(); // pick one
            dto.setRole(firstRole);
        } else {
            dto.setRole("employee"); // default/fallback
        }

        if(employeeProfile!=null){
            dto.setDepartment(employeeProfile.getDepartment());
            dto.setAbout(employeeProfile.getAbout());
            dto.setPhone(employeeProfile.getPhone());
            dto.setAvatarUrl(employeeProfile.getAvatarUrl());
            dto.setLocation(employeeProfile.getLocation());
        }
       return dto;
    }
}





