package com.example.Portal.Service;

import com.example.Portal.Dto.OutboxDTO;
import com.example.Portal.Repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OutboxService {

    @Autowired
    OutboxRepository outboxRepository;


    public List<OutboxDTO> getOutboxservice(){
        return outboxRepository.findAll().stream().map(OutboxDTO::new).collect(Collectors.toList());

    }
}
