package com.example.Portal.Controller;

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
    @PreAuthorize("isAuthenticated")
    @GetMapping("/me/history")
    public ResponseEntity<?> getHistory(@RequestParam int days,@AuthenticationPrincipal String employeeId){
        return ResponseEntity.ok(attandanceService.nDays(employeeId,days));
    }

    @PreAuthorize("isAuthenticated")
    @GetMapping("/me/month")
    public ResponseEntity<?> getMonthYear(@RequestParam int year,@RequestParam int month,@AuthenticationPrincipal String employeeId){
        return ResponseEntity.ok(attandanceService.getMonths(employeeId,year,month));
    }
}
