package com.example.ticketservice.routing;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class TicketRouter {

    private final OpenAIClient client;

    public TicketRouter() {
        // Uses OPENAI_API_KEY (and optionally ORG / PROJECT) from environment
        this.client = OpenAIOkHttpClient.fromEnv();
    }

    /**
     * Returnerer hvilken afdeling en ticket skal til
     * baseret på subject + body.
     */
    public DepartmentName routeDepartment(String subject, String body) {
        String prompt = buildPrompt(subject, body);

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt)
                // Vælg et gyldigt modelnavn
                .model(ChatModel.GPT_5_1)   // eller GPT_4_1, GPT_4_O_MINI, osv.
                .build();

        ChatCompletion completion = client
                .chat()
                .completions()
                .create(params);

        // content() er en Optional<String>
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
            return DepartmentName.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            System.out.println("Kunne ikke mappe AI-svar '" + raw + "' til Department, sætter UNKNOWN");
            return DepartmentName.UNKNOWN;
        }
    }

    private String buildPromptDepartment(String subject, String body) {
        return """
                Du er en routing-motor for IT-support tickets hos itm8.

                Du får en ticket (subject + body) og skal vælge PRÆCIS én afdeling.

                Vælg KUN mellem disse afdelinger (brug kun selve nøgleordet som svar):
                - SERVICE_DESK_L1: Første linje support, simple brugerproblemer, password reset, låst konto, “min pc virker ikke”, printer, standard software.
                - SERVICE_DESK_L2: Mere komplekse incidents, fejl der kræver dybere fejlsøgning, eskalering fra L1.
                - WORKPLACE: Klienter, laptops, desktop, standard image, Intune/Endpoint, Office 365 klient, Teams-klient.
                - NETWORK: Netværk, VPN, firewall, Wifi, routere, switches, netværksforbindelse.
                - SECURITY: Antivirus, EDR, security alerts, phishing, kompromitterede konti, sikkerhedspolitikker.
                - ERP: ERP-systemer (fx Business Central, Navision, SAP, AX), finans, indkøb, lager.
                - CRM: CRM-løsninger, kundesystemer, salgs-pipeline, CRM-integration.
                - UNKNOWN: Hvis du virkelig ikke kan afgøre det.

                Regler:
                - Svar KUN med navnet på afdelingen: fx SERVICE_DESK_L1
                - Ingen forklaring, ingen ekstra tekst.

                Ticket subject:
                %s

                Ticket body:
                %s
                """.formatted(subject, body);
    }
    public PriorityName routePriority(String subject, String body) {
        String prompt = buildPrompt(subject, body);

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt)
                // Vælg et gyldigt modelnavn
                .model(ChatModel.GPT_5_1)   // eller GPT_4_1, GPT_4_O_MINI, osv.
                .build();

        ChatCompletion completion = client
                .chat()
                .completions()
                .create(params);

        // content() er en Optional<String>
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
            return PriorityName.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            System.out.println("Kunne ikke mappe AI-svar '" + raw + "' til Priority, sætter P3");
            return PriorityName.P3;
        }
    }

    private String buildPrompt(String subject, String body) {
        return """
                Du er en routing-motor for IT-support tickets hos itm8.

                Du får en ticket (subject + body) og skal vælge PRÆCIS én prioritet.

                Vælg KUN mellem disse prioriteter (brug kun selve nøgleordet som svar):
                - SIMA: HOST DOWN, CRITICAL ALERT, SERVICE NOT RESPONDING, FILESYSTEM CRITICAL, CPU 100, MEMORY CRITICAL.
                - P1: SERVER DOWN, SYSTEM DOWN, OUTAGE, OFFLINE, NETWORK DOWN, INTERNET DOWN, VPN DOWN, CRITICAL ALERT, EMERGENCY, HIGH SEVERITY, STORAGE FAILURE, HOST DOWN, ESXI HOST OFFLINE, POWER OUTAGE, BACKUP FAILURE,
                                      DATABASE DOWN, AUTHENTICATION SERVICE DOWN, DNS FAILURE.
                - P2: SLOW PERFORMANCE, HIGH CPU, HIGH MEMORY, NETWORK UNSTABLE, PACKET LOSS, LATENCY, BACKUP WARNING,
                                      VM FREEZE, HYPER-V ISSUE, DEGRADE PERFORMANCE, DISK WARNING, FAILS OCCASIONALLY.
                - P3: RDP ISSUE, VPN NOT WORKING, PRINTER NOT WORKING, CANNOT LOGIN, UPDATE NEEDED,
                                      MAINTENANCE, SMALL ERROR, CONFIGURATION CHANGE, GROUP POLICY REQUEST, CREATE USER, RESET PASSWORD.

                Regler:
                - Svar KUN med navnet på afdelingen: fx P1
                - Ingen forklaring, ingen ekstra tekst.

                Ticket subject:
                %s

                Ticket body:
                %s
                """.formatted(subject, body);
    }
}
