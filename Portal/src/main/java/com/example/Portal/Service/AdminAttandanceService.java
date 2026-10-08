package com.example.Portal.Service;


import com.example.Portal.Repository.AttandanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminAttandanceService {

    @Autowired
    AttandanceRepository attandanceRepository;


    public List<com.example.Portal.Dto.AdminAttandanceDto> getattandanceAdmin(){
        return attandanceRepository.findAll().stream().map(com.example.Portal.Dto.AdminAttandanceDto::new).collect(Collectors.toList());
    }
}
