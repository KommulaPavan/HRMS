package com.example.Portal.Controller;


import com.example.Portal.Dto.LeaveCalendarDTO;
import com.example.Portal.Service.LeaveCalendarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-calendar")
public class LeaveCalendarController {

    private final LeaveCalendarService leaveCalendarService;

    public LeaveCalendarController(LeaveCalendarService leaveCalendarService) {
        this.leaveCalendarService = leaveCalendarService;
    }

    // -------- Single row upsert (expects ONE object) --------
    // PUT /api/leave-calendar/{id}
  /*  @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LeaveCalendarDTO> upsertOne(
            @PathVariable("id") String id,
            @RequestBody LeaveCalendarDTO dto) {

        LeaveCalendarDTO saved = leaveCalendarService.upsertRow(id, dto);
        return ResponseEntity.ok(saved);
    }

    // Optional: single row upsert without path id (service will use dto.id or compute)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/item", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LeaveCalendarDTO> upsertOneNoPath(@RequestBody LeaveCalendarDTO dto) {
        LeaveCalendarDTO saved = leaveCalendarService.upsertRow(null, dto);
        return ResponseEntity.ok(saved);
    }

    // -------- Bulk replace (expects an ARRAY) --------
    // PUT /api/leave-calendar
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<LeaveCalendarDTO>> replaceAll(@RequestBody List<LeaveCalendarDTO> rows) {
        List<LeaveCalendarDTO> result = leaveCalendarService.replaceCalendar(rows);
        return ResponseEntity.ok(result);
    }

   */
    @PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
    @GetMapping
    public List<LeaveCalendarDTO> list() {
        return leaveCalendarService.getALl();
    }

    // -------- Single row upsert (admin only) --------
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LeaveCalendarDTO> upsertOne(
            @PathVariable String id,
            @RequestBody LeaveCalendarDTO dto) {
        return ResponseEntity.ok(leaveCalendarService.upsertRow(id, dto));
    }

    // Optional: upsert without id in path
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/item", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LeaveCalendarDTO> upsertOneNoPath(@RequestBody LeaveCalendarDTO dto) {
        return ResponseEntity.ok(leaveCalendarService.upsertRow(null, dto));
    }

    // -------- Bulk replace (admin only) --------
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<LeaveCalendarDTO>> replaceAll(@RequestBody List<LeaveCalendarDTO> rows) {
        return ResponseEntity.ok(leaveCalendarService.replaceCalendar(rows));
    }

    // Optional delete (admin only)
   /* @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        leaveCalendarService.deleteRow(id);
        return ResponseEntity.noContent().build();
    }*/
}

