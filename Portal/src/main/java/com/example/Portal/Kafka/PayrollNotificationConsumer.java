package com.example.Portal.Kafka;

import com.example.Portal.Dto.PayrollEvent;
import com.example.Portal.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PayrollNotificationConsumer {

    @Autowired
    KafkaEventProduce kafkaEventProduce;
    @Autowired
    MailService mailService;

    @KafkaListener(topics = "payroll-topic", groupId = "notification-id")
    public void handelPayrollEnvent(PayrollEvent event){

        String subject=event.getMonth();
        String body="Dear Employee,\n\n"
                + "Your salary of ₹" + event.getNet()
                + " for " + event.getMonth()
                + " has been credited.\n\nRegards,\nHR Team";

        mailService.sendLinkPayroll(event.getEmail(), subject,body);

        System.out.println("Sending mail to: " + event.getEmail());

    }
}
