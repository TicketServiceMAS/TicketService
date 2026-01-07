package com.example.ticketservice.routing;

import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.Mail;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.messaging.EmailReceiver;
import com.example.ticketservice.messaging.EmailSender;
import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.PriorityRepository;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketRouter {

    private final OpenAIClient client;
    private final DepartmentRepository departmentRepository;
    private final PriorityRepository priorityRepository;

    private final EmailReceiver emailReceiver;
    private final EmailSender emailSender;

    public TicketRouter(DepartmentRepository departmentRepository,
                        PriorityRepository priorityRepository,
                        EmailReceiver emailReceiver,
                        EmailSender emailSender) {
        this.departmentRepository = departmentRepository;
        this.priorityRepository = priorityRepository;
        this.emailReceiver = emailReceiver;
        this.emailSender = emailSender;
        this.client = OpenAIOkHttpClient.fromEnv();
    }

    public void analyzeMail() {
        List<Mail> mails = emailReceiver.receiveMail();
        if (!mails.isEmpty()) {
            for (Mail mail : mails) {
                analyzer(mail);
                mail.setID();
                emailSender.sendMail(mail);
            }
        }
    }

    public List<String> getDepartmentNames(){
        List<Department> departments = departmentRepository.findAll();
        List<String> names = new ArrayList<>();
        for (Department department : departments){
            names.add(department.getDepartmentName());
        }
        return names;
    }

    public List<String> getPriorityNames(){
        List<Priority> priorities = priorityRepository.findAll();
        List<String> names = new ArrayList<>();
        for (Priority priority : priorities){
            names.add(priority.getPriorityName());
        }
        return names;
    }

    /**
     * Sætter Department og Priority på mailen
     * baseret på subject + body via OpenAI-routing.
     */
    public void analyzer(Mail mail) {
        String departmentName = routeDepartment(mail.subject, mail.content);
        Department department = departmentRepository.getDepartmentByDepartmentName(departmentName);
        System.out.println("The department is: " + department.getDepartmentName());

        String priorityName = routePriority(mail.subject, mail.content);
        Priority priority = priorityRepository.getPriorityByPriorityName(priorityName);
        System.out.println("The priority is: " + priority.getPriorityName());

        mail.setDepartment(department);
        mail.setPriority(priority);
    }

    /**
     * Finder department-navn (SERVICE_DESK_L1, SERVICE_DESK_L2, WORKPLACE, NETWORK, SECURITY, ERP, CRM, DEFAULT)
     */
    public String routeDepartment(String subject, String body) {
        List<String> departmentNames = getDepartmentNames();
        String prompt = buildPromptDepartment(departmentNames, subject, body);

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt)
                .model(ChatModel.GPT_5_1)
                .build();

        ChatCompletion completion = client
                .chat()
                .completions()
                .create(params);

        String raw = completion
                .choices()
                .get(0)
                .message()
                .content()
                .orElse("")
                .trim();

        String normalized = raw
                .replace("\"", "")
                .replace("`", "")
                .trim()
                .toUpperCase();

        try {
            return normalized;
        } catch (IllegalArgumentException e) {
            System.out.println("Kunne ikke mappe AI-svar '" + raw + "' til Department, sætter DEFAULTED");
            return "DEFAULTED";
        }
    }

    private String buildPromptDepartment(List<String> names, String subject, String body) {

        String departments = String.join("\n", names);

        return """
                Du fungerer som en deterministisk routing-motor for IT-supporttickets hos itm8.
                Dit eneste ansvar er at analysere en tickets subject + body og vælge præcis én gældende afdeling.

                Du må aldrig give forklaringer, begrundelser, fritekst, ekstra ord, synonymer eller formatering.
                Du må kun svare med én af de prædefinerede nøgleord nedenfor. Ingen undtagelser.

                TILLADTE AFDELINGER (brug KUN nøgleordet):
                
                
                %s

                Ticket subject:
                %s

                Ticket body:
                %s
                """.formatted(departments, subject, body);
    }

    /**
     * Finder priority-navn (SIMA, P1, P2, P3)
     */
    public String routePriority(String subject, String body) {
        List<String> priorityNames = getPriorityNames();
        String prompt = buildPromptPriority(priorityNames, subject, body);

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt)
                .model(ChatModel.GPT_5_1)
                .build();

        ChatCompletion completion = client
                .chat()
                .completions()
                .create(params);

        String raw = completion
                .choices()
                .get(0)
                .message()
                .content()
                .orElse("")
                .trim();

        String normalized = raw
                .replace("\"", "")
                .replace("`", "")
                .trim()
                .toUpperCase();

        try {
            return normalized;
        } catch (IllegalArgumentException e) {
            System.out.println("Kunne ikke mappe AI-svar '" + raw + "' til Priority, sætter P3");
            return "P3";
        }
    }

    private String buildPromptPriority(List<String>names, String subject, String body) {

        String priorities = String.join("\n", names);

        return """
                Du fungerer som en deterministisk prioriterings-motor for IT-supporttickets hos itm8.
                Dit eneste ansvar er at analysere en tickets subject + body og vælge præcis én prioritet.

                Du må aldrig give forklaringer, begrundelser, fritekst, ekstra ord, synonymer eller formatering.
                Du må kun svare med én af de prædefinerede nøgleord nedenfor: SIMA, P1, P2 eller P3. Ingen undtagelser.

                PRIORITETER (skrevet fra højest til lavest):

                %s

                Hvis alvorlighedsgraden er uklar:
                - Hvis der ikke er klare tegn på SIMA/P1/P2 -> vælg P3 som standard.

                ABSOLUTTE REGLER:
                1. Svar KUN med én af følgende: SIMA, P1, P2 eller P3.
                2. Ingen forklaringer, ingen ekstra tekst, ingen symboler.
                3. Hvis flere niveauer nævnes i teksten, så vælg den HØJESTE alvor, der tydeligt støttes af subject/body.
                4. Hvis der både er typiske SIMA-ord og P1-ord → vurder om det primært er overvågnings-/host-alert (SIMA)
                   eller bruger-/forretnings-outage (P1). Vælg derefter den mest passende.
                5. Hvis der primært er performance- eller stabilitetsproblemer uden fuld nedetid → P2.
                6. Hvis det ligner almindelige tickets, brugersager eller service requests → P3.
                7. Svaret må aldrig indeholde mellemrum, punktum eller ekstra ord.

                Nedenfor får du ticket-data. Brug reglerne ovenfor til at vælge præcis én prioritet.

                Ticket subject:
                %s

                Ticket body:
                %s
                """.formatted(priorities, subject, body);
    }
}
