package com.example.Portal.Client;

import com.example.Portal.Dto.AttadanceRecord;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static org.springframework.web.client.RestClientUtils.getBody;

public class AttadanceClient {

    RestTemplate restTemplate=new RestTemplate();

    String uri="";

    public List<AttadanceRecord> attadanceRecords(Date From,Date To){

        restTemplate.exchange(uri, HttpMethod.GET,null, ParameterizedTypeReference<List<AttadanceRecord>>() {}).getBody()

    }

}
