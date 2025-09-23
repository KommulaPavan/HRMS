package com.example.Portal.Dto;

import com.example.Portal.Entity.LeaveCalendar;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveCalendarDTO {

    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOf;
    private String typeOf;
    private String holidayType;
    private String empId;
    private String status;
    private Integer entitled;
    private Integer used;

    public LeaveCalendarDTO() {}

    public LeaveCalendarDTO(LeaveCalendar entity){

            this.id= entity.getId();
            this.dateOf = entity.getDateOf();
            this.typeOf = entity.getTypeOf();
            this.holidayType = entity.getHolidayType();
            this.empId = entity.getEmpId();
            this.status = entity.getStatus();
            this.entitled=entity.getEntitled();
            this.used=entity.getUsed();
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDateOf() {
        return dateOf;
    }

    public void setDateOf(LocalDate dateOf) {
        this.dateOf = dateOf;
    }

    public String getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEntitled() {
        return entitled;
    }

    public void setEntitled(Integer entitled) {
        this.entitled = entitled;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }
}
