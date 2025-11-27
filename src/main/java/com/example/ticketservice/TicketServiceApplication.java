package com.example.ticketservice;

import com.example.ticketservice.entity.Mail;
import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.PriorityRepository;
import com.example.ticketservice.service.EmailReceiver;
import com.example.ticketservice.service.EmailSender;
import com.example.ticketservice.util.DepartmentName;
import com.example.ticketservice.util.PriorityName;
import com.example.ticketservice.service.TicketRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class TicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }

  /*  @Bean
    public CommandLineRunner demoRouter() {
        return args -> {
            TicketRouter router = new TicketRouter();

            String subject = "Bruger kan ikke logge på VPN";
            String body = "Hej, jeg kan ikke forbinde til VPN hjemmefra, får fejl 619. Host Down og jeg har en small error";

            DepartmentName dep = router.routeDepartment(subject, body);

            System.out.println("=== AI ROUTING TEST ===");
            System.out.println("Subject: " + subject);
            System.out.println("Routed department: " + dep);


            PriorityName pri = router.routePriority(subject, body);

            System.out.println("Routed priority: " + pri);
        };
    }*/

    @Component
    public class EmailTestRunner implements CommandLineRunner {

        private final DepartmentRepository departmentRepository;
        private final PriorityRepository priorityRepository;

        private final EmailReceiver emailReceiver;
        private final EmailSender emailSender;
        private final TicketRouter ticketRouter;

        @Autowired// Spring injicerer de nødvendige objekter her
        public EmailTestRunner(
                DepartmentRepository departmentRepository,
                PriorityRepository priorityRepository,
                EmailSender emailSender,
                TicketRouter ticketRouter,
                EmailReceiver emailReceiver) {
            this.departmentRepository = departmentRepository;
            this.priorityRepository = priorityRepository;
            this.emailSender = emailSender;
            this.ticketRouter = ticketRouter;
            this.emailReceiver = emailReceiver;
        }

        @Override
        public void run(String... args) throws Exception {
            System.out.println("--- Starter mail test ---");

            Mail mail = new Mail();
            mail.setID();
            mail.setSubject("hello");
            mail.setContent("mine ERP-systemer og indkøb virker ikke");

            // Din logik, nu med injicerede afhængigheder
            //List<Mail> mails = emailReceiver.receiveMail();
            //for (Mail mail : mails){
              //  mail.setID();
            ticketRouter.analyzer(mail);
            System.out.println(mail.getDepartment().getDepartmentName());
            emailSender.sendMail(mail);

            System.out.println("--- Mail sendt (forhåbentlig) ---");
            // Hvis du kun vil sende mailen én gang, kan du lukke appen her:
            // System.exit(0);
        }
    }
}
