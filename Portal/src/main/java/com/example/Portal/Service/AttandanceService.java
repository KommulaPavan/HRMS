package com.example.Portal.Service;


import com.example.Portal.Dto.AttadanceRecord;
import com.example.Portal.Dto.AttandanceRequest;
import com.example.Portal.Entity.Attandance;
import com.example.Portal.Entity.Employee;
import com.example.Portal.Repository.AttandanceRepository;
import com.example.Portal.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
    public class AttandanceService {

        @Autowired
        private AttandanceRepository attRepo;

        @Autowired
        private EmployeeRepository employeeRepo;

        private String resolveEmployeeId(String principal) {
            return employeeRepo.findByEmployeeId(principal)
                    .or(() -> employeeRepo.findByEmail(principal))
                    .map(Employee::getEmployeeId)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    "Employee not found for principal: " + principal));
        }

        @Transactional
        public AttandanceRequest checkIn(String principal, String notes, String source) {
            String employeeId = resolveEmployeeId(principal);
            LocalDate today = LocalDate.now();

            Attandance a = attRepo.findTodayBest(employeeId, today)
                    .orElseGet(() -> {
                        Employee emp = employeeRepo.findByEmployeeId(employeeId)
                                .orElseThrow(() ->
                                        new UsernameNotFoundException(
                                                "Employee not found: " + employeeId));

                        Attandance n = new Attandance();
                        n.setEmployee(emp);
                        n.setTodayDate(today);
                        return n;
                    });

            if (a.getCheckInAt() == null) {
                a.setCheckInAt(LocalDateTime.now());
                a.setAttandancestatus("PRESENT");
            }

            if (notes != null) a.setNotes(notes);
            a.setSource(source != null ? source : "WEB");

            return new AttandanceRequest(attRepo.save(a));
        }

        @Transactional
        public AttandanceRequest checkOut(String principal, String notes, String source) {
            String employeeId = resolveEmployeeId(principal);
            LocalDate today = LocalDate.now();

            Attandance a = attRepo.findTodayBest(employeeId, today)
                    .orElseThrow(() ->
                            new IllegalStateException("No check-in found for today"));

            if (a.getCheckOutAt() == null) {
                a.setCheckOutAt(LocalDateTime.now());

                if (a.getCheckInAt() != null) {
                    long mins = Duration.between(
                            a.getCheckInAt(),
                            a.getCheckOutAt()
                    ).toMinutes();
                    a.setWorkMinutes((int) Math.max(0, mins));
                }
            }

            if (notes != null) a.setNotes(notes);
            if (source != null) a.setSource(source);

            return new AttandanceRequest(attRepo.save(a));
        }

        // Single clean "today" method
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

        public List<AttandanceRequest> getAll() {
            return attRepo.findAll()
                    .stream()
                    .map(AttandanceRequest::new)
                    .toList();
        }

        public List<AttandanceRequest> nDays(String employeeId, int days) {
            LocalDate end = LocalDate.now();
            LocalDate start = end.minusDays(days - 1);

            return attRepo
                    .findByEmployeeEmployeeIdAndTodayDateBetweenOrderByTodayDateDesc(
                            employeeId, start, end)
                    .stream()
                    .map(AttandanceRequest::new)
                    .toList();
        }

        public List<AttandanceRequest> getMonths(String employeeId, int year, int month) {
            YearMonth ym = YearMonth.of(year, month);
            LocalDate start = ym.atDay(1);
            LocalDate end = ym.atEndOfMonth();

            return attRepo
                    .findByEmployeeEmployeeIdAndTodayDateBetweenOrderByTodayDateDesc(
                            employeeId, start, end)
                    .stream()
                    .map(AttandanceRequest::new)
                    .toList();
        }

        public List<AttandanceRequest> getBetweenDates(
                String principal,
                LocalDate from,
                LocalDate to
        ) {
            if (from == null || to == null) {
                throw new IllegalArgumentException("From and To dates are required");
            }
            if (from.isAfter(to)) {
                throw new IllegalArgumentException(
                        "'from' date cannot be after 'to' date");
            }

            String employeeId = resolveEmployeeId(principal);

            return attRepo
                    .findByEmployeeEmployeeIdAndTodayDateBetweenOrderByTodayDateDesc(
                            employeeId, from, to)
                    .stream()
                    .map(AttandanceRequest::new)
                    .toList();
        }

        public List<AttadanceRecord> getAttendanceForPayroll(
                LocalDate from,
                LocalDate to
        ) {
            return attRepo
                    .findByTodayDateBetween(from, to)
                    .stream()
                    .map(AttadanceRecord::new)   // IMPORTANT
                    .toList();
        }

    }













