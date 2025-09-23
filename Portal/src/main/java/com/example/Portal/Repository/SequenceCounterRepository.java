package com.example.Portal.Repository;

import com.example.Portal.Entity.SequenceCounter;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SequenceCounterRepository extends JpaRepository<SequenceCounter,String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SequenceCounter s where s.dateKey = :dateKey")
    Optional<SequenceCounter> lockByDateKey(@Param("dateKey") String dateKey);
}
