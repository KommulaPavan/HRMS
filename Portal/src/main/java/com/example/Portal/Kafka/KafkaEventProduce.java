package com.example.Portal.Kafka;

import com.example.Portal.Dto.PayrollEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class KafkaEventProduce {

    @Autowired
    private KafkaTemplate<String, PayrollEvent> kafkaTemplate;

    public void sendPayrollGenerateEvent(String employeeId, String email, BigDecimal net
    ,String month){

        PayrollEvent event=new PayrollEvent(employeeId,email,net,month);

        kafkaTemplate.send("payroll-topic",event);

    }

}
