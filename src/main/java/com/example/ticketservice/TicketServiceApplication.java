package com.example.ticketservice;

import com.example.ticketservice.service.TicketRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
            ticketRouter.analyzeMail();
        }
    }
}
