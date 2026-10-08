package com.example.Portal.Repository;


import com.example.Portal.Entity.LeaveCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveCalendarRepository extends JpaRepository<LeaveCalendar,String> {
}
