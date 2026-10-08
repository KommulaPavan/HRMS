

package com.example.Portal.Service;


import com.example.Portal.Dto.EmployeeProfileDto;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Entity.EmployeeProfile;
import com.example.Portal.Repository.EmployeeProfileRepository;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class EmployeeProfileService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeProfileService.class);

    // absolute base directory for avatars (change if required)
   // private static final Path AVATAR_BASE = Path.of(System.getProperty("user.home"), "portal-data", "uploads", "avatars").toAbsolutePath();
    private static final Path AVATAR_BASE =
            Path.of("logs", "images").toAbsolutePath();
    private final EmployeeRepository employeeRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final RoleRepository roleRepository;

    public EmployeeProfileService(EmployeeRepository employeeRepository,
                                  EmployeeProfileRepository employeeProfileRepository,
                                  RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public EmployeeProfileDto updateProfile(EmployeeProfileDto dto, MultipartFile file) {
        if (dto.getEmployeeId() == null || dto.getEmployeeId().isBlank()) {
            throw new IllegalArgumentException("employeeId is required");
        }

        Employee emp = employeeRepository.findByEmployeeId(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee id not found"));

        EmployeeProfile profile = employeeProfileRepository.findByEmployeeId(dto.getEmployeeId())
                .orElseGet(() -> {
                    EmployeeProfile p = new EmployeeProfile();
                    p.setEmployee(emp);
                    p.setEmployeeId(emp.getEmployeeId());
                    return p;
                });

        if (profile.getEmployee() == null) {
            profile.setEmployee(emp);
        }

        // copy fields
        profile.setName(dto.getName());
        profile.setEmail(dto.getEmail() != null ? dto.getEmail() : emp.getEmail());
        profile.setDepartment(dto.getDepartment());
        profile.setPhone(dto.getPhone());
        profile.setAbout(dto.getAbout());
        profile.setLocation(dto.getLocation());

        // handle file
        if (file != null && !file.isEmpty()) {
            if (file.getSize() > 200 * 1024) {
                throw new RuntimeException("Avatar must be <= 200KB");
            }

            String original = file.getOriginalFilename();
            String ext = StringUtils.getFilenameExtension(original);
            if (ext == null || ext.isBlank()) ext = "png";
            ext = ext.toLowerCase();

            try {
                Files.createDirectories(AVATAR_BASE);
                Path dest = AVATAR_BASE.resolve(profile.getEmployeeId() + "." + ext);
                try (InputStream in = file.getInputStream()) {
                    Files.copy(in, dest, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
                String publicUrl = "/files/avatars/" + profile.getEmployeeId() + "." + ext + "?t=" + System.currentTimeMillis();
                profile.setAvatarUrl(publicUrl);
                log.info("Saved avatar for {} -> {}", profile.getEmployeeId(), dest.toAbsolutePath());
            } catch (IOException e) {
                log.error("Failed to store avatar for " + profile.getEmployeeId(), e);
                throw new RuntimeException("Failed to store avatar", e);
            }
        } else if (dto.getAvatarUrl() != null && !dto.getAvatarUrl().isBlank()) {
            profile.setAvatarUrl(dto.getAvatarUrl());
        }

        EmployeeProfile saved = employeeProfileRepository.save(profile);

        // build and return DTO
        EmployeeProfileDto out = new EmployeeProfileDto();
        out.setEmployeeId(saved.getEmployeeId());
        out.setName(saved.getName());
        out.setEmail(saved.getEmail());
        out.setDepartment(saved.getDepartment());
        out.setPhone(saved.getPhone());
        out.setAbout(saved.getAbout());
        out.setLocation(saved.getLocation());
        out.setAvatarUrl(saved.getAvatarUrl());

        // set role if available
        if (emp.getRole() != null && !emp.getRole().isEmpty()) {
            Optional.of(emp.getRole().iterator().next()).ifPresent(r -> out.setRole(r.getName()));
        } else {
            out.setRole("employee");
        }
        log.info("Avatar folder path: {}", AVATAR_BASE.toAbsolutePath());

        return out;
    }

    // existing read method adapted to return DTO (kept for convenience)
    public EmployeeProfileDto getProfile(String employeeId) {
        Employee emp = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee id not found"));
        EmployeeProfile profile = employeeProfileRepository.findByEmployeeId(employeeId).orElse(null);

        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setEmployeeId(emp.getEmployeeId());
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        if (emp.getRole() != null && !emp.getRole().isEmpty()) {
            dto.setRole(emp.getRole().iterator().next().getName());
        } else {
            dto.setRole("employee");
        }

        if (profile != null) {
            dto.setDepartment(profile.getDepartment());
            dto.setAbout(profile.getAbout());
            dto.setPhone(profile.getPhone());
            dto.setAvatarUrl(profile.getAvatarUrl());
            dto.setLocation(profile.getLocation());
        }
        return dto;
    }
}
//
//
//
//
//
////package com.example.Portal.Service;
////
////import com.example.Portal.Dto.EmployeeProfileDto;
////import com.example.Portal.Entity.Employee;
////import com.example.Portal.Entity.EmployeeProfile;
////import com.example.Portal.Entity.Role;
////import com.example.Portal.Repository.EmployeeProfileRepository;
////import com.example.Portal.Repository.EmployeeRepository;
////import com.example.Portal.Repository.RoleRepository;
////import jakarta.transaction.Transactional;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////import org.springframework.web.multipart.MultipartFile;
////
////import java.nio.file.Files;
////import java.nio.file.Path;
////import java.util.Optional;
////import java.util.Set;
////
////@Service
////public class EmployeeProfileService {
////
////    @Autowired
////    EmployeeRepository employeeRepository;
////    @Autowired
////    EmployeeProfileRepository employeeProfileRepository;
////    @Autowired
////    RoleRepository roleRepository;
////    // Service (Multipart version)
////    @Transactional
////    public String updateProfile(EmployeeProfileDto dto, MultipartFile file) {
////        if (dto.getEmployeeId() == null || dto.getEmployeeId().isBlank()) {
////            throw new IllegalArgumentException("employeeId is required");
////        }
////
////        // 1) Load a MANAGED Employee (not 'new Employee()')
////        Employee emp = employeeRepository.findByEmployeeId(dto.getEmployeeId())
////                .orElseThrow(() -> new RuntimeException("Employee id not found"));
////
////        // 2) Upsert the profile
////        EmployeeProfile profile = employeeProfileRepository.findByEmployeeId(dto.getEmployeeId())
////                .orElseGet(() -> {
////                    EmployeeProfile p = new EmployeeProfile();
////                    // IMPORTANT: wire the required association
////                    p.setEmployee(emp);
////                    // If you also keep a string column:
////                    p.setEmployeeId(emp.getEmployeeId());
////                    return p;
////                });
////
////        // If existing row somehow lacks the link, fix it
////        if (profile.getEmployee() == null) {
////            profile.setEmployee(emp);
////        }
////
////        // 3) Copy fields
////        profile.setName(dto.getName());
////        profile.setEmail(dto.getEmail() != null ? dto.getEmail() : emp.getEmail());
////        profile.setDepartment(dto.getDepartment());
////        profile.setPhone(dto.getPhone());
////        profile.setAbout(dto.getAbout());
////        profile.setLocation(dto.getLocation());
////
////        // optional avatar file
////        if (file != null && !file.isEmpty()) {
////            if (file.getSize() > 200 * 1024) throw new RuntimeException("Avatar must be <= 200KB");
////            String ext = org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename());
////            String safeExt = (ext != null ? ext.toLowerCase() : "png");
////            java.nio.file.Path dir = java.nio.file.Path.of("uploads", "avatars");
////            try {
////                java.nio.file.Files.createDirectories(dir);
////                java.nio.file.Path dest = dir.resolve(profile.getEmployeeId() + "." + safeExt);
////                java.nio.file.Files.copy(file.getInputStream(), dest, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
////                profile.setAvatarUrl("/files/avatars/" + profile.getEmployeeId() + "." + safeExt);
////            } catch (Exception e) {
////                throw new RuntimeException("Failed to store avatar", e);
////            }
////        } else if (dto.getAvatarUrl() != null && !dto.getAvatarUrl().isBlank()) {
////            profile.setAvatarUrl(dto.getAvatarUrl());
////        }
////
////        employeeProfileRepository.save(profile);
////        return "profile updated successfully";
////    }
////
////   /* public String updateProfile(EmployeeProfileDto employeeProfileDto, MultipartFile file) {
////        Employee employee = employeeRepository.findByEmail(employeeProfileDto.getEmail()).orElseThrow(() -> new RuntimeException("Email is not found"));
////        EmployeeProfile employeeProfile = employeeProfileRepository.findByEmployeeId(employeeProfileDto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Profile id is not found"));
////        employeeProfile.setEmployeeId(employeeProfileDto.getEmployeeId());
////        employeeProfile.setName(employeeProfileDto.getName());
////        employeeProfile.setEmail(employeeProfileDto.getEmail());
////        employeeProfile.setDepartment(employeeProfileDto.getDepartment());
////        employeeProfile.setPhone(employeeProfileDto.getPhone());
////        employeeProfile.setAbout(employeeProfileDto.getAbout());
////
////        if (file != null && !file.isEmpty()) {
////            if (file.getSize() > 200 * 1024) {
////                throw new RuntimeException("Avatar must be <= 200KB");
////            }
////            String ext = org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename());
////            String safeExt = (ext != null ? ext.toLowerCase() : "png");
////
////            // Example: save to disk (could also be S3, DB as BLOB, etc.)
////            try {
////                Path dir = Path.of("uploads", "avatars");
////                Files.createDirectories(dir);
////                Path dest = dir.resolve(employeeProfile.getEmployeeId() + "." + safeExt);
////                Files.copy(file.getInputStream(), dest, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
////
////                // Save URL/path into DB
////                String url = "/files/avatars/" + employeeProfile.getEmployeeId() + "." + safeExt;
////                employeeProfile.setAvatarUrl(url);
////            } catch (Exception e) {
////                throw new RuntimeException("Failed to store avatar", e);
////            }
////        } else {
////            // No file: keep the DTO-provided avatar URL (if any)
////            if (employeeProfileDto.getAvatarUrl() != null && !employeeProfileDto.getAvatarUrl().isBlank()) {
////                employeeProfile.setAvatarUrl(employeeProfileDto.getAvatarUrl());
////            }
////        }
////        employeeProfile.setLocation(employeeProfileDto.getLocation());
////        employeeProfileRepository.save(employeeProfile);
////        return "profile updated sucessfully";
////    }*/
////
////    public EmployeeProfileDto updateProfile(String employeeId){
//////        Role adminrole=roleRepository.findByName("ROLE_ADMIN").orElseGet(()-> {
//////            Role role = new Role();
//////            role.setName("ROLE_ADMIN");
//////            roleRepository.save(role);
////
////        Employee emp=employeeRepository.findByEmployeeId(employeeId)
////                .orElseThrow(()-> new RuntimeException("Employee id not found"));
////        EmployeeProfile employeeProfile=employeeProfileRepository.findByEmployeeId(employeeId).orElse(null);
////        EmployeeProfileDto dto=new EmployeeProfileDto();
////        dto.setEmployeeId(emp.getEmployeeId());
////        dto.setName(emp.getName());
////        dto.setEmail(emp.getEmail());
////        if (emp.getRole() != null && !emp.getRole().isEmpty()) {
////            String firstRole = emp.getRole().iterator().next().getName(); // pick one
////            dto.setRole(firstRole);
////        } else {
////            dto.setRole("employee"); // default/fallback
////        }
////
////        if(employeeProfile!=null){
////            dto.setDepartment(employeeProfile.getDepartment());
////            dto.setAbout(employeeProfile.getAbout());
////            dto.setPhone(employeeProfile.getPhone());
////            dto.setAvatarUrl(employeeProfile.getAvatarUrl());
////            dto.setLocation(employeeProfile.getLocation());
////        }
////       return dto;
////    }
////}
////
////
////
////
////
