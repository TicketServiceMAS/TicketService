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
    public Department route(String subject, String body) {
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
            return Department.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            System.out.println("Kunne ikke mappe AI-svar '" + raw + "' til Department, sætter UNKNOWN");
            return Department.UNKNOWN;
        }
    }

    private String buildPrompt(String subject, String body) {
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
}
