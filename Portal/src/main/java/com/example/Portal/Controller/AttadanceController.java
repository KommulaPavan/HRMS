/*package com.example.Portal.Controller;

import com.example.Portal.Dto.AttandanceRequest;
import com.example.Portal.Dto.AttandanceRequestClock;
import com.example.Portal.Dto.EmployeeClockDTO;
import com.example.Portal.Dto.EmployeeRequest;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Service.AttandanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/attendance")
public class AttadanceController {

    @Autowired
    AttandanceService attandanceService;
    @Autowired
    EmployeeRepository employeeRepository;
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/check-in")
    public ResponseEntity<AttandanceRequest> checkIn(Authentication auth,
                                                     @RequestBody(required = false) AttandanceRequest body) {
        String principal = auth.getName();                         // set by JwtAuthFilter
        String employeeId = resolveEmployeeId(principal);          // works for either email or id
        AttandanceRequest dto = attandanceService.checkIn(employeeId,
                body != null ? body.getNotes() : null,
                body != null ? body.getSource() : "WEB");
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/check-out")
    public ResponseEntity<AttandanceRequest> checkOut(Authentication auth,
                                                      @RequestBody(required = false) AttandanceRequest body) {
        String principal = auth.getName();
        String employeeId = resolveEmployeeId(principal);
        AttandanceRequest dto = attandanceService.checkOut(employeeId,
                body != null ? body.getNotes() : null,
                body != null ? body.getSource() : "WEB");
        return ResponseEntity.ok(dto);
    }

    private String resolveEmployeeId(String principal) {
        // If principal already IS employeeId, this hits first and returns fast.
        return employeeRepository.findByEmployeeId(principal)
                .or(() -> employeeRepository.findByEmail(principal))
                .map(Employee::getEmployeeId)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found for principal: " + principal));
    }
    @PreAuthorize("isAuthenticated")
    @GetMapping("/me/today")
    public ResponseEntity<?> getToadyAttendence(@AuthenticationPrincipal(expression = "username") String username){
            AttandanceRequest request=attandanceService.toady(username);
            return ResponseEntity.ok(request);
    }



   /* @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','HR')")
    @GetMapping("/history")
    public List<AttandanceRequest> getAttandanceHistory(@RequestParam String employeeId){
         return attandanceService.getAttadance(employeeId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
    @GetMapping("/employees")
        public List<EmployeeClockDTO> allEmployee(){
        return attandanceService.getAll();
    }*/
  /*  @PreAuthorize("isAuthenticated")
    @GetMapping("/me/history")
    public ResponseEntity<?> getHistory(@RequestParam int days,@AuthenticationPrincipal String employeeId){
        return ResponseEntity.ok(attandanceService.nDays(employeeId,days));
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/me/month")
    public ResponseEntity<?> getMonthYear(@RequestParam int year,@RequestParam int month,@AuthenticationPrincipal String employeeId){
        return ResponseEntity.ok(attandanceService.getMonths(employeeId,year,month));
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/me/month")
    public ResponseEntity<List<AttandanceRequest>> getattandance(){
        return ResponseEntity.ok(attandanceService.getALl());
    }

}*/

package com.example.Portal.Controller;


import com.example.Portal.Dto.AttadanceRecord;
import com.example.Portal.Dto.AttandanceRequest;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.EmployeeRepository;
import com.example.Portal.Service.AttandanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")

public class AttadanceController {

    @Autowired
    AttandanceService attandanceService;
    @Autowired
    EmployeeRepository employeeRepository;

    /* ---------- Helpers ---------- */
    private String resolveEmployeeIdFromPrincipal(String principal) {
        // If JWT subject is already the employeeId, return it as-is; otherwise map from email -> employeeId
        return employeeRepository.findByEmployeeId(principal)
                .or(() -> employeeRepository.findByEmail(principal))
                .map(Employee::getEmployeeId)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found for principal: " + principal));
    }

    /* ---------- Punch ---------- */
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN','HR','MANAGER')")
    @PostMapping("/check-in")
    public ResponseEntity<AttandanceRequest> checkIn(Authentication auth,
                                                     @RequestBody(required = false) AttandanceRequest body) {
        String employeeId = resolveEmployeeIdFromPrincipal(auth.getName());
        String notes  = body != null ? body.getNotes()  : null;
        String source = body != null ? body.getSource() : "WEB";
        AttandanceRequest dto = attandanceService.checkIn(employeeId, notes, source);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN','HR','MANAGER')")
    @PostMapping("/check-out")
    public ResponseEntity<AttandanceRequest> checkOut(Authentication auth,
                                                      @RequestBody(required = false) AttandanceRequest body) {
        String employeeId = resolveEmployeeIdFromPrincipal(auth.getName());
        String notes  = body != null ? body.getNotes()  : null;
        String source = body != null ? body.getSource() : "WEB";
        AttandanceRequest dto = attandanceService.checkOut(employeeId, notes, source);
        return ResponseEntity.ok(dto);
    }

    /* ---------- Me (UI uses these) ---------- */

    // Today
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/today")
    public ResponseEntity<AttandanceRequest> getToday(Authentication auth) {
        String employeeId = resolveEmployeeIdFromPrincipal(auth.getName());
        // If your service method is 'toady', call that; otherwise 'today'
        AttandanceRequest dto = attandanceService.today(employeeId);
        return ResponseEntity.ok(dto);
    }

    // Save notes for today (frontend calls PATCH /attendance/me/today/notes)
//    @PreAuthorize("isAuthenticated()")
//    @PatchMapping("/me/today/notes")
//    public ResponseEntity<AttandanceRequest> updateTodayNotes(Authentication auth,
//                                                              @RequestBody Map<String,String> body) {
//        String employeeId = resolveEmployeeIdFromPrincipal(auth.getName());
//        String notes = body != null ? body.getOrDefault("notes", "") : "";
//        AttandanceRequest dto = attandanceService.updateTodayNotes(employeeId, notes);
//        return ResponseEntity.ok(dto);
//    }

    // History last N days
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/history")
    public ResponseEntity<List<AttandanceRequest>> getHistory(
            @RequestParam(defaultValue = "14") int days,
            Authentication auth
    ) {
        String employeeId = resolveEmployeeIdFromPrincipal(auth.getName());
        return ResponseEntity.ok(attandanceService.nDays(employeeId, days));
    }

    // Specific month (YYYY, M)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/month")
    public ResponseEntity<List<AttandanceRequest>> getMonth(
            @RequestParam int year,
            @RequestParam int month,
            Authentication auth
    ) {
        String employeeId = resolveEmployeeIdFromPrincipal(auth.getName());
        return ResponseEntity.ok(attandanceService.getMonths(employeeId, year, month));
    }

    /* ---------- Admin/HR/Manager (query someone else) ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    @GetMapping("/history")
    public ResponseEntity<List<AttandanceRequest>> getHistoryFor(
            @RequestParam String employeeId,
            @RequestParam(defaultValue = "14") int days
    ) {
        return ResponseEntity.ok(attandanceService.nDays(employeeId, days));
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    @GetMapping("/month")
    public ResponseEntity<List<AttandanceRequest>> getMonthFor(
            @RequestParam String employeeId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResponseEntity.ok(attandanceService.getMonths(employeeId, year, month));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/range")
    public ResponseEntity<List<AttandanceRequest>> getBetween(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to,
            Authentication auth
    ) {
        return ResponseEntity.ok(
                attandanceService.getBetweenDates(auth.getName(), from, to)
        );
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("me/rangee")
    public List<AttadanceRecord> getForPayroll(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return attandanceService.getAttendanceForPayroll(from, to);
    }


}

