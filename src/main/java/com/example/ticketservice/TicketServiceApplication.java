package com.example.ticketservice;

import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.PriorityRepository;
import com.example.ticketservice.service.EmailReceiver;
import com.example.ticketservice.service.EmailSender;
import com.example.ticketservice.service.TicketRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class TicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }
    @Component
    public class EmailRunner implements CommandLineRunner {

        //private final DepartmentRepository departmentRepository;
        //private final PriorityRepository priorityRepository;
        //private final EmailSender emailSender;
        private final TicketRouter ticketRouter;
        //private final EmailReceiver emailReceiver;

        @Autowired// Spring injicerer de nødvendige objekter her
        public EmailRunner(
                //DepartmentRepository departmentRepository,
                //PriorityRepository priorityRepository,
                //EmailSender emailSender,
                TicketRouter ticketRouter, EmailReceiver emailReceiver) {
            //this.departmentRepository = departmentRepository;
            //this.priorityRepository = priorityRepository;
            //this.emailSender = emailSender;
            this.ticketRouter = ticketRouter;
            //this.emailReceiver = emailReceiver;
        }
        @Override
        public void run(String... args) throws Exception {
        ticketRouter.AnalyzeMail();
            System.out.println("Analyzing and sending mail");}
    }
}
