package com.example.Portal.Service;

import com.example.Portal.Dto.LeaveCalendarDTO;
import com.example.Portal.Entity.LeaveCalendar;
import com.example.Portal.Repository.LeaveCalendarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/*@Service
public class LeaveCalendarService {

    @Autowired
    LeaveCalendarRepository leaveCalendarRepository;

    public String LeaveCalendar(LeaveCalendarDTO leaveCalendarDTO,String id){
        LeaveCalendar leaveCalendar=new LeaveCalendar();
        leaveCalendar.setId(id);
        leaveCalendar.setDateOf(leaveCalendarDTO.getDateOf());
        leaveCalendar.setEmpId(leaveCalendarDTO.getEmpId());
        leaveCalendar.setTypeOf(leaveCalendarDTO.getTypeOf());
        leaveCalendar.setHolidayType(leaveCalendarDTO.getHolidayType());
        leaveCalendar.setStatus("Pending");
        leaveCalendarRepository.save(leaveCalendar);
        return "Add leave Sucessfully";

    }

    public List<LeaveCalendarDTO> getLeave(){
        return leaveCalendarRepository.findAll().stream().map(LeaveCalendarDTO::new).collect(Collectors.toList());
    }

    public void deleteRow(String id){
        leaveCalendarRepository.deleteById(id);
    }*/
    @Service
    public class LeaveCalendarService {

        private final LeaveCalendarRepository repo;

        public LeaveCalendarService(LeaveCalendarRepository repo) {
            this.repo = repo;
        }

        /** Build a deterministic manual id when client didn't send one. */
        private static String makeId(LocalDate date, String type) {
            String safeType = (type == null ? "HOLIDAY" : type.trim().toUpperCase().replaceAll("\\s+", "_"));
            return safeType + "-" + date;   // e.g. DIWALI-2025-10-31
        }

        @Transactional
        public List<LeaveCalendarDTO> replaceCalendar(List<LeaveCalendarDTO> rows) {
            if (rows == null) rows = List.of();

            // (optional) ensure unique dates in the file
            Set<LocalDate> seen = new HashSet<>();

            List<LeaveCalendar> entities = new ArrayList<>();
            for (LeaveCalendarDTO dto : rows) {
                if (dto.getDateOf() == null && dto.getDateOf() == null) {
                    throw new IllegalArgumentException("date is required");
                }
                LocalDate date = dto.getDateOf() != null ? dto.getDateOf() : dto.getDateOf();
                if (dto.getTypeOf() == null && dto.getTypeOf() == null) {
                    throw new IllegalArgumentException("type is required");
                }
                String type = dto.getTypeOf() != null ? dto.getTypeOf() : dto.getTypeOf();

                // optional: avoid duplicate date rows during replace
                if (!seen.add(date)) {
                    throw new IllegalArgumentException("duplicate holiday date: " + date);
                }

                LeaveCalendar e = new LeaveCalendar();

                // ✅ MANUAL ID: use client id if provided, otherwise compute one
                String id = (dto.getId() != null && !dto.getId().isBlank())
                        ? dto.getId()
                        : makeId(date, type);

                e.setId(id);                                  // <-- CRITICAL
                e.setDateOf(date);
                e.setTypeOf(type.trim());
                e.setHolidayType(
                        dto.getHolidayType() != null ? dto.getHolidayType() : "Company Holiday");
                e.setEmpId(dto.getEmpId() != null ? dto.getEmpId() : "HOLIDAY");
                e.setStatus(dto.getStatus() != null ? dto.getStatus() : "APPROVED");

                entities.add(e);
            }

            repo.deleteAllInBatch();
            repo.saveAll(entities);                           // all ids set

            return repo.findAll()
                    .stream()
                    .sorted(Comparator.comparing(LeaveCalendar::getDateOf))
                    .map(LeaveCalendarDTO::new)
                    .toList();
        }

        @Transactional
        public LeaveCalendarDTO upsertRow(String id, LeaveCalendarDTO dto) {
            // derive working date & type from either modern (date/type) or legacy (dateOf/typeOf)
            LocalDate date = dto.getDateOf() != null ? dto.getDateOf() : dto.getDateOf();
            String type = dto.getTypeOf() != null ? dto.getTypeOf() : dto.getTypeOf();

            if (date == null) throw new IllegalArgumentException("date is required");
            if (type == null || type.isBlank()) throw new IllegalArgumentException("type is required");

            // Decide the id: prefer URL path; else body; else computed
            String Id = (id != null && !id.isBlank())
                    ? id
                    : (dto.getId() != null && !dto.getId().isBlank())
                    ? dto.getId()
                    : makeId(date, type);

            LeaveCalendar entity = repo.findById(Id).orElseGet(LeaveCalendar::new);
            entity.setId(Id);                                  // <-- CRITICAL
            entity.setDateOf(date);
            entity.setTypeOf(type.trim());
            entity.setHolidayType(
                    dto.getHolidayType() != null ? dto.getHolidayType() : "Company Holiday");
            entity.setEmpId(dto.getEmpId() != null ? dto.getEmpId() : "HOLIDAY");
            entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "APPROVED");


            return new LeaveCalendarDTO(repo.save(entity));    // id is set
        }

        public List<LeaveCalendarDTO> getALl(){
            return repo.findAll().stream().map(LeaveCalendarDTO::new).collect(Collectors.toList());
        }
    }


