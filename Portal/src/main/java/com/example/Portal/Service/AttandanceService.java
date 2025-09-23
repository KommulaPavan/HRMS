package com.example.Portal.Service;

import com.example.Portal.Dto.AttandanceRequest;
import com.example.Portal.Dto.AttandanceRequestClock;
import com.example.Portal.Dto.EmployeeClockDTO;
import com.example.Portal.Entity.Attandance;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.AttandanceRepository;
import com.example.Portal.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.coyote.http11.Constants.a;

@Service
public class AttandanceService {

    @Autowired
    AttandanceRepository attRepo;

    @Autowired
    EmployeeRepository employeeRepo;

    private String resolveEmployeeId(String principal) {
        // First, try direct employeeId
        return employeeRepo.findByEmployeeId(principal)
                .or(() -> employeeRepo.findByEmail(principal))
                .map(Employee::getEmployeeId)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found for principal: " + principal));
    }



    @Transactional
    public AttandanceRequest checkIn(String principal, String notes, String source) {
        String employeeId = resolveEmployeeId(principal);
        LocalDate today = LocalDate.now();

        Attandance a = attRepo.findTodayBest(employeeId, today)
                .orElseGet(() -> {
                    Employee emp = employeeRepo.findByEmployeeId(employeeId)
                            .orElseThrow(() -> new UsernameNotFoundException("Employee not found: " + employeeId));
                    Attandance n = new Attandance();
                    n.setEmployee(emp);
                    n.setTodayDate(today);
                    return n;
                });

        if (a.getCheckInAt() == null) {
            a.setCheckInAt(LocalDateTime.now());
            a.setAttandancestatus("PRESENT");
        }
        if (notes != null)  a.setNotes(notes);
        a.setSource(source != null ? source : "WEB");

        return new AttandanceRequest(attRepo.save(a));
    }

    @Transactional
    public AttandanceRequest checkOut(String principal, String notes, String source) {
        String employeeId = resolveEmployeeId(principal);
        LocalDate today = LocalDate.now();

        Attandance a = attRepo.findTodayBest(employeeId, today)
                .orElseThrow(() -> new IllegalStateException("No check-in found for today"));

        if (a.getCheckOutAt() == null) {
            a.setCheckOutAt(LocalDateTime.now());
            if (a.getCheckInAt() != null) {
                long mins = Duration.between(a.getCheckInAt(), a.getCheckOutAt()).toMinutes();
                a.setWorkMinutes((int) Math.max(0, mins));
            }
        }
        if (notes != null)  a.setNotes(notes);
        if (source != null) a.setSource(source);

        return new AttandanceRequest(attRepo.save(a));
    }

    public AttandanceRequest today(String principal) {
        String employeeId = resolveEmployeeId(principal);
        LocalDate today = LocalDate.now();

        return attRepo.findTodayBest(employeeId, today)
                .map(AttandanceRequest::new)
                .orElseGet(() -> {
                    AttandanceRequest dto = new AttandanceRequest();
                    dto.setEmployeeId(employeeId);
                    dto.setTodayDate(today);
                    dto.setAttandancestatus("ABSENT");
                    dto.setWorkMinutes(0);
                    return dto;
                });
    }


    /*public List<AttandanceRequest> getAttadance(String emplyoeeId){
        Employee employee=employeeRepository.findByEmployeeId(emplyoeeId).orElseThrow(()->new RuntimeException("EmployeeId not found"));
       return attandanceRepository.findByEmployee(employee).stream().map(AttandanceRequest::new).collect(Collectors.toList());
    }

    public List<EmployeeClockDTO> getAll(){
        List<Employee> all=employeeRepository.findAll();
        return all.stream().map(EmployeeClockDTO::new).collect(Collectors.toList());
    }*/
    public List<AttandanceRequest> getALl() {
        return attRepo.findAll().stream().map(AttandanceRequest::new).collect(Collectors.toList());
    }

    public AttandanceRequest toady(String principalValue) {
        Employee emp = employeeRepo.findByEmail(principalValue)
                .orElseGet(() -> employeeRepo.findByEmployeeId(principalValue)
                        .orElseThrow(() -> new RuntimeException("Employee not found")));
        String employeeId = emp.getEmployeeId();
        LocalDate Today = LocalDate.now();
        Optional<Attandance> record = attRepo
                .findTodayBest(employeeId, Today);
        if (record.isPresent()) {
            return record.map(AttandanceRequest::new)
                    .orElseGet(() -> {
                        AttandanceRequest dto = new AttandanceRequest();
                        dto.setEmployeeId(employeeId);
                        dto.setTodayDate(Today);
                        dto.setAttandancestatus("ABSENT");
                        dto.setWorkMinutes(0);
                        return dto;
                    });

        }
        return null;
    }


    public List<AttandanceRequest> nDays(String employeeId, int days){
        LocalDate end=LocalDate.now();
        LocalDate start=end.minusDays(days-1);
        List<Attandance> records=attRepo.findByEmployeeEmployeeIdAndTodayDateBetweenOrderByTodayDateDesc(employeeId,start,end);
        return records.stream().map(AttandanceRequest::new).collect(Collectors.toList());
    }

    public List<AttandanceRequest> getMonths(String employeeId,int year,int month){
        YearMonth ym=toYearMonth(year, month);
        LocalDate start=ym.atDay(1);
        LocalDate end=ym.atEndOfMonth();
        List<Attandance> records=attRepo.findByEmployeeEmployeeIdAndTodayDateBetweenOrderByTodayDateDesc(employeeId,start,end);
        return records.stream().map(AttandanceRequest::new).collect(Collectors.toList());

    }
    private YearMonth toYearMonth(int year,int month){
        try {
            return YearMonth.of(year, month); // validates month 1–12
        } catch (DateTimeException ex) {
            throw new IllegalArgumentException("Invalid year/month: " + year + "-" + month, ex);
        }

    }



}








