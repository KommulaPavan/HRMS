package com.example.Portal.Repository;


import com.example.Portal.Entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository
        extends JpaRepository<Announcement, Long>, JpaSpecificationExecutor<Announcement> {

    @Override
    Optional<Announcement> findById(Long id);

    List<Announcement> findAllByOrderByPublishAtDesc();

}

