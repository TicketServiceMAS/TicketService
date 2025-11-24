package com.example.ticketservice;

import com.example.ticketservice.routing.Department;
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
            String body = "Hej, jeg kan ikke forbinde til VPN hjemmefra, får fejl 619.";

            Department dep = router.route(subject, body);

            System.out.println("=== AI ROUTING TEST ===");
            System.out.println("Subject: " + subject);
            System.out.println("Routed department: " + dep);
        };
    }
}
