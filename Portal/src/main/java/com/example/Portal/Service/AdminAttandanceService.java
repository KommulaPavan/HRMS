package com.example.Portal.Service;

import com.example.Portal.Dto.AdminAttandanceDto;
import com.example.Portal.Repository.AttandanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminAttandanceService {

    @Autowired
    AttandanceRepository attandanceRepository;


    public List<AdminAttandanceDto> getattandanceAdmin(){
        return attandanceRepository.findAll().stream().map(AdminAttandanceDto::new).collect(Collectors.toList());
    }
}
