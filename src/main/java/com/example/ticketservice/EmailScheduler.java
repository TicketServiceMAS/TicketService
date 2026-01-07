package com.example.ticketservice;

import com.example.ticketservice.routing.TicketRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class EmailScheduler {

    private final TicketRouter ticketRouter;

    @Autowired
    public EmailScheduler(TicketRouter ticketRouter) {
        this.ticketRouter = ticketRouter;
    }

    @Scheduled(fixedRate = 20000)
    public void runEmailAnalyzer() {
        ticketRouter.analyzeMail();
    }
}
