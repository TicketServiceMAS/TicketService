package com.example.ticketservice;

import com.example.ticketservice.routing.DepartmentName;
import com.example.ticketservice.routing.PriorityName;
import com.example.ticketservice.routing.TicketRouter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoRouter() {
        return args -> {
            TicketRouter router = new TicketRouter();

            String subject = "Bruger kan ikke logge på VPN";
            String body = "Hej, jeg kan ikke forbinde til VPN hjemmefra, får fejl 619. Jeg oplever også meget høj CPU";

            DepartmentName dep = router.routeDepartment(subject, body);

            System.out.println("=== AI ROUTING TEST ===");
            System.out.println("Subject: " + subject);
            System.out.println("Routed department: " + dep);


            PriorityName pri = router.routePriority(subject, body);

            System.out.println("Routed priority: " + pri);
        };
    }
}
