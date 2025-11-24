package com.example.ticketservice;

import com.example.ticketservice.entity.Mail;
import com.example.ticketservice.service.EmailReceiver;

import java.util.List;

public class main {
    public static void main(String[] args) {
        EmailReceiver emailReceiver = new EmailReceiver();
        List<Mail> mails = emailReceiver.receiveMail();
        for (Mail mail : mails){
            System.out.println("SUBJECT: " + mail.getSubject() + " CONTENT: " + mail.getContent());
        }
    }
}
