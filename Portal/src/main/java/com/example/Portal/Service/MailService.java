package com.example.Portal.Service;

import com.example.Portal.Dto.MailMessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;


    public String sendLinkToMail(MailMessageDAO mailMessageDAO) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Use getters instead of setters
        String id=mailMessageDAO.getId();
        mailMessage.setTo(mailMessageDAO.getTo());
        mailMessage.setSubject(mailMessageDAO.getSubject());

        // Build email body
        StringBuilder body = new StringBuilder();
        if (mailMessageDAO.getBody() != null) {
            body.append(mailMessageDAO.getBody());
        }
        if (mailMessageDAO.getLink() != null) {
            body.append("\n\nActivation link: ").append(mailMessageDAO.getLink());
        }
        if (mailMessageDAO.isActivation()) {
            body.append("\n\nYour account has been activated.");
        }
        if (mailMessageDAO.getCreatedAt() != null) {
            body.append("\n\nCreated at: ").append(mailMessageDAO.getCreatedAt().toString());
        }

        mailMessage.setText(body.toString());

        javaMailSender.send(mailMessage);
        return "Mail is sent";
    }
}


