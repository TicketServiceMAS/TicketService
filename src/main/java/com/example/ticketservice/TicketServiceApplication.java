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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableScheduling
public class TicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }

    @Component
    public class EmailScheduler {

        private final TicketRouter ticketRouter;

        @Autowired
        public EmailScheduler(TicketRouter ticketRouter) {
            this.ticketRouter = ticketRouter;
        }

        // Runs every 60 seconds
        @Scheduled(fixedRate = 20000)
        public void runEmailAnalyzer() {
            System.out.println("Ran analyzer");
            ticketRouter.AnalyzeMail();
        }
    }
}
