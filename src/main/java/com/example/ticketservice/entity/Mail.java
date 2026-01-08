package com.example.ticketservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Mail {

    public String ID;
    public String mailAddress;
    public String subject;
    public String content;

    public Department department;
    public Priority priority;

    public Mail(String subject, String content) {
        this.subject = subject;
        this.content = content;

    }

    public void setID(){
        this.ID = UUID.randomUUID().toString();
     }

}
