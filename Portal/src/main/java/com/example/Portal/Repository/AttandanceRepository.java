package com.example.Portal.Repository;


import com.example.Portal.Entity.Attandance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttandanceRepository extends JpaRepository<Attandance, Long> {

    // Prefer today's row that is NOT checked out yet; if none, return the most recent
    @Query("""
    select a from Attandance a
    where a.employee.employeeId = :employeeId
      and a.todayDate = :today
    order by case when a.checkOutAt is null then 0 else 1 end, a.checkInAt desc
  """)
    List<Attandance> findTodayOrdered(@Param("employeeId") String employeeId,
                                      @Param("today") LocalDate today);

    default Optional<Attandance> findTodayBest(@Param("employeeId")String employeeId, @Param("today") LocalDate today) {
        List<Attandance> list = findTodayOrdered(employeeId, today);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
    @Query("""
        select a from Attandance a
        where a.employee.employeeId = :employeeId
        and a.todayDate between :startDate and :endDate
        order by a.todayDate desc
        """)
    List<Attandance> findByEmployeeEmployeeIdAndTodayDateBetweenOrderByTodayDateDesc(
            @Param("employeeId") String employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<Attandance> findByTodayDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );
}
