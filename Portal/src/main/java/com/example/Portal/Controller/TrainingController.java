/*package com.example.Portal.Controller;

import com.example.Portal.Dto.EnrollRequest;
import com.example.Portal.Dto.EnrollResponse;
import com.example.Portal.Dto.RosterItemDto;
import com.example.Portal.Dto.TrainingRequestDto;
import com.example.Portal.Service.TrainingEnrollmentService;
import com.example.Portal.Service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrainingController {
    @Autowired
    TrainingService trainingService;
    @Autowired
    TrainingEnrollmentService enrollmentService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/admin/trainings")
    public ResponseEntity<?> addTraining(@RequestBody TrainingRequestDto trainingRequestDto){
        return ResponseEntity.ok(trainingService.assignTraining(trainingRequestDto));
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PostMapping("/api/hr/trainings")
    public ResponseEntity<?> addTrainingHr(@RequestBody TrainingRequestDto dto) {
        return ResponseEntity.ok(trainingService.assignTraining(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/trainings")
    public List<TrainingRequestDto> gettraining(){
        return trainingService.getTrainingDetails();
    }

    // pieces only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/admin/trainings/{id}/enroll")
    public EnrollResponse enrollAdmin(@PathVariable String id, @RequestBody EnrollRequest body) {
        return enrollmentService.enroll(id, body.getEmployeeIds());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/trainings/{trainingId}/roster")
    public List<RosterItemDto> rosterAdmin(@PathVariable String trainingId) {
        return enrollmentService.roster(trainingId);
    }

}
package com.example.Portal.Controller;

import com.example.Portal.Dto.EnrollRequest;
import com.example.Portal.Dto.EnrollResponse;
import com.example.Portal.Dto.RosterItemDto;
import com.example.Portal.Dto.TrainingRequestDto;
import com.example.Portal.Service.TrainingEnrollmentService;
import com.example.Portal.Service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class TrainingController {

    @Autowired
    TrainingService trainingService;
    @Autowired
    TrainingEnrollmentService enrollmentService;

    /* ========= CREATE ========= */
   /* @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/admin/trainings")
    public ResponseEntity<?> addTraining(@RequestBody TrainingRequestDto dto){
        return ResponseEntity.ok(trainingService.assignTraining(dto));
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PostMapping("/api/hr/trainings")
    public ResponseEntity<?> addTrainingHr(@RequestBody TrainingRequestDto dto) {
        return ResponseEntity.ok(trainingService.assignTraining(dto));
    }

    /* ========= LIST ========= */
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/api/admin/trainings")
//    public List<TrainingRequestDto> getAdminTrainings(){
//        return trainingService.getTrainingDetails();
//    }
//
//    // HR list (your frontend tries /api/hr/trainings)
//    @PreAuthorize("hasAnyRole('HR','ADMIN')")
//    @GetMapping("/api/hr/trainings")
//    public List<TrainingRequestDto> getHrTrainings() {
//        return trainingService.getTrainingDetails();
//    }
//
//    // SELF list (your frontend tries /api/trainings first)
//    // Use isAuthenticated() so any logged-in user can load their view.
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/api/trainings")
//    public List<TrainingRequestDto> getSelfTrainings() {
//        // If you have a scoped method per user, call that instead.
//        return trainingService.getTrainingDetails();
//    }
//    // TrainingController (add these)
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/api/trainings/{trainingId}/roster")
//    public List<RosterItemDto> rosterSelfPath(@PathVariable String trainingId) {
//        return enrollmentService.roster(trainingId);
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/api/trainings/{trainingId}/enrollments")
//    public List<RosterItemDto> enrollmentsSelfPath(@PathVariable String trainingId) {
//        return enrollmentService.roster(trainingId);
//    }
//
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/api/trainings/roster")
//    public List<RosterItemDto> rosterSelf(@RequestParam String trainingId) {
//        return enrollmentService.roster(trainingId);
//    }
//
//
//    /* ========= ENROLL ========= */
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/api/admin/trainings/{id}/enroll")
//    public EnrollResponse enrollAdmin(@PathVariable String id, @RequestBody EnrollRequest body) {
//        return enrollmentService.enroll(id, body.getEmployeeIds());
//    }
//
//    // HR enroll (your frontend tries /api/hr/trainings/{id}/enroll)
//    @PreAuthorize("hasAnyRole('HR','ADMIN')")
//    @PostMapping("/api/hr/trainings/{id}/enroll")
//    public EnrollResponse enrollHr(@PathVariable String id, @RequestBody EnrollRequest body) {
//        return enrollmentService.enroll(id, body.getEmployeeIds());
//    }
//
//    // Alias: body carries {trainingId, employeeIds}; frontend falls back to these
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/api/admin/trainings/enroll")
//    public EnrollResponse enrollAdminBody(@RequestBody EnrollBody body) {
//        return enrollmentService.enroll(body.getTrainingId(), body.getEmployeeIds());
//    }
//
//    @PreAuthorize("hasAnyRole('HR','ADMIN')")
//    @PostMapping("/api/hr/trainings/enroll")
//    public EnrollResponse enrollHrBody(@RequestBody EnrollBody body) {
//        return enrollmentService.enroll(body.getTrainingId(), body.getEmployeeIds());
//    }
//
//    /* ========= ROSTER / ENROLLMENTS ========= */
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/api/admin/trainings/{trainingId}/roster")
//    public List<RosterItemDto> rosterAdmin(@PathVariable String trainingId) {
//        return enrollmentService.roster(trainingId);
//    }
//
//    // Your frontend also probes /enrollments as an alias
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/api/admin/trainings/{trainingId}/enrollments")
//    public List<RosterItemDto> enrollmentsAdmin(@PathVariable String trainingId) {
//        return enrollmentService.roster(trainingId);
//    }
//
//    // HR roster/enrollments (frontend probes these too)
//    @PreAuthorize("hasAnyRole('HR','ADMIN')")
//    @GetMapping("/api/hr/trainings/{trainingId}/roster")
//    public List<RosterItemDto> rosterHr(@PathVariable String trainingId) {
//        return enrollmentService.roster(trainingId);
//    }
//
//    @PreAuthorize("hasAnyRole('HR','ADMIN')")
//    @GetMapping("/api/hr/trainings/{trainingId}/enrollments")
//    public List<RosterItemDto> enrollmentsHr(@PathVariable String trainingId) {
//        return enrollmentService.roster(trainingId);
//    }
//
//    // Query-param fallbacks: /admin|/hr/trainings/roster?trainingId=...
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/api/admin/trainings/roster")
//    public List<RosterItemDto> rosterAdminQuery(@RequestParam String trainingId) {
//        return enrollmentService.roster(trainingId);
//    }
//
//    @PreAuthorize("hasAnyRole('HR','ADMIN')")
//    @GetMapping("/api/hr/trainings/roster")
//    public List<RosterItemDto> rosterHrQuery(@RequestParam String trainingId) {
//        return enrollmentService.roster(trainingId);
//    }
//
//    /* ========= request bodies for alias endpoints ========= */
//    public static class EnrollBody {
//        private String trainingId;
//        private List<String> employeeIds;
//
//        public String getTrainingId() { return trainingId; }
//        public void setTrainingId(String trainingId) { this.trainingId = trainingId; }
//        public List<String> getEmployeeIds() { return employeeIds; }
//        public void setEmployeeIds(List<String> employeeIds) { this.employeeIds = employeeIds; }
//    }
//}
package com.example.Portal.Controller;

import com.example.Portal.Dto.EnrollRequest;
import com.example.Portal.Dto.EnrollResponse;
import com.example.Portal.Dto.RosterItemDto;
import com.example.Portal.Dto.TrainingRequestDto;
import com.example.Portal.Service.TrainingEnrollmentService;
import com.example.Portal.Service.TrainingService;
//import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingEnrollmentService enrollmentService;

    public TrainingController(TrainingService trainingService,
                              TrainingEnrollmentService enrollmentService) {
        this.trainingService = trainingService;
        this.enrollmentService = enrollmentService;
    }

    /* ================= CREATE ================= */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/trainings")
    public ResponseEntity<TrainingRequestDto> addTraining( @RequestBody TrainingRequestDto dto){
        TrainingRequestDto saved = trainingService.assignTraining(dto);
        return ResponseEntity
                .created(URI.create("/api/admin/trainings/" + saved.getTrainingId()))
                .body(saved);
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PostMapping("/hr/trainings")
    public ResponseEntity<TrainingRequestDto> addTrainingHr( @RequestBody TrainingRequestDto dto) {
        TrainingRequestDto saved = trainingService.assignTraining(dto);
        return ResponseEntity
                .created(URI.create("/api/hr/trainings/" + saved.getTrainingId()))
                .body(saved);
    }

    /* ================= LIST ================= */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/trainings")
    public List<TrainingRequestDto> getAdminTrainings(){
        return trainingService.getTrainingDetails();
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @GetMapping("/hr/trainings")
    public List<TrainingRequestDto> getHrTrainings() {
        return trainingService.getTrainingDetails();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/trainings")
    public List<TrainingRequestDto> getSelfTrainings() {
        return trainingService.getTrainingDetails();
    }

    /* ================= UPDATE ================= */
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PatchMapping({"/admin/trainings/{id}", "/hr/trainings/{id}"})
    public TrainingRequestDto updateTraining(@PathVariable("id") String trainingId,
                                             @RequestBody TrainingRequestDto patch) {
        return trainingService.updateTraining(trainingId, patch);
    }

    /* ================= DELETE ================= */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/trainings/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable("id") String trainingId) {
        trainingService.deleteTraining(trainingId);
        return ResponseEntity.noContent().build();
    }

    /* =============== ENROLL ================= */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/trainings/{id}/enroll")
    public EnrollResponse enrollAdmin(@PathVariable String id, @RequestBody EnrollRequest body) {
        return enrollmentService.enroll(id, body.getEmployeeIds());
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @PostMapping("/hr/trainings/{id}/enroll")
    public EnrollResponse enrollHr(@PathVariable String id, @RequestBody EnrollRequest body) {
        return enrollmentService.enroll(id, body.getEmployeeIds());
    }

    /* ============== ROSTER/ENROLLMENTS ============== */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping({"/admin/trainings/{trainingId}/roster", "/admin/trainings/{trainingId}/enrollments"})
    public List<RosterItemDto> rosterAdmin(@PathVariable String trainingId) {
        return enrollmentService.roster(trainingId);
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @GetMapping({"/hr/trainings/{trainingId}/roster", "/hr/trainings/{trainingId}/enrollments"})
    public List<RosterItemDto> rosterHr(@PathVariable String trainingId) {
        return enrollmentService.roster(trainingId);
    }

    // Self variants (your client tries these if admin/hr fail)
    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/trainings/{trainingId}/roster", "/trainings/{trainingId}/enrollments"})
    public List<RosterItemDto> rosterSelfPath(@PathVariable String trainingId) {
        return enrollmentService.roster(trainingId);
    }

    // Query-param fallbacks: /admin|/hr|/trainings/roster?trainingId=...
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/trainings/roster")
    public List<RosterItemDto> rosterAdminQuery(@RequestParam String trainingId) {
        return enrollmentService.roster(trainingId);
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @GetMapping("/hr/trainings/roster")
    public List<RosterItemDto> rosterHrQuery(@RequestParam String trainingId) {
        return enrollmentService.roster(trainingId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/trainings/roster")
    public List<RosterItemDto> rosterSelfQuery(@RequestParam String trainingId) {
        return enrollmentService.roster(trainingId);
    }
}




