package com.example.ticketservice.service;

import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.Mail;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.PriorityRepository;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.stereotype.Service;

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

    public void AnalyzeMail() {
        List<Mail> mails = emailReceiver.receiveMail();
        if (!mails.isEmpty()) {
            for (Mail mail : mails) {
                analyzer(mail);
                mail.setID();
                emailSender.sendMail(mail);
            }
        }
    }

    /**
     * Sætter Department og Priority på mailen
     * baseret på subject + body via OpenAI-routing.
     */
    public void analyzer(Mail mail) {
        String departmentName = routeDepartment(mail.subject, mail.content);
        Department department = departmentRepository.getDepartmentByDepartmentName(departmentName);

        String priorityName = routePriority(mail.subject, mail.content);
        Priority priority = priorityRepository.getPriorityByPriorityName(priorityName);

        mail.setDepartment(department);
        mail.setPriority(priority);
    }

    /**
     * Finder department-navn (SERVICE_DESK_L1, SERVICE_DESK_L2, WORKPLACE, NETWORK, SECURITY, ERP, CRM, DEFAULT)
     */
    public String routeDepartment(String subject, String body) {
        String prompt = buildPromptDepartment(subject, body);

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

    private String buildPromptDepartment(String subject, String body) {
        return """
                Du fungerer som en deterministisk routing-motor for IT-supporttickets hos itm8.
                Dit eneste ansvar er at analysere en tickets subject + body og vælge præcis én gældende afdeling.

                Du må aldrig give forklaringer, begrundelser, fritekst, ekstra ord, synonymer eller formatering.
                Du må kun svare med én af de prædefinerede nøgleord nedenfor. Ingen undtagelser.

                TILLADTE AFDELINGER (brug KUN nøgleordet):

                SERVICE_DESK_L1
                Vælg denne, når ticketen omhandler:
                - Simple og almindelige brugerproblemer.
                - Password reset, udløbet kode, MFA-problemer, låst konto.
                - "Min PC virker ikke", langsom computer, grundlæggende troubleshooting.
                - Printere, netværksprintere (hvis det er brugerproblem, ikke netværk).
                - Standardsoftware: Outlook-klient, Office-programmer, browserfejl, standardinstallationer.
                - Generelle spørgsmål uden teknisk kompleksitet.
                - Tickets der tydeligt hører til første niveau før eskalation.

                SERVICE_DESK_L2
                Vælg denne, når ticketen:
                - Er eskaleret fra L1 eller kræver dybere teknisk fejlsøgning.
                - Indeholder logs, komplekse fejl, systemintegrationer, avanceret klientproblematik.
                - Beskriver ikke-standardiserede eller tilbagevendende fejl.
                - Tydeligt ligger over hvad L1 håndterer.

                WORKPLACE
                Vælg denne når ticketen omhandler klienter og endpoint-løsninger:
                - Hardware: laptops, desktops, docking stations, monitors, periferiudstyr.
                - OS-image, standard build, deployment.
                - Intune / Endpoint Manager / device compliance.
                - Office 365-klientinstallationer, Teams-klient, OneDrive-klient.
                - Klient-applicationsproblemer der ikke er rene, simple brugerspørgsmål (ellers SERVICE_DESK_L1).
                - Maskinopsætning, udskiftning, onboarding af nyt IT-udstyr.

                NETWORK
                Vælg denne når ticketen omhandler netværksinfrastruktur:
                - Netværksforbindelse, intet net på lokationer, kabel/port problemer.
                - Firewalls (on-prem, cloud), VPN, IPSec, SSL-VPN.
                - Wifi: access points, dårlig dækning, wifi-konfiguration.
                - Switches, routere, routing, VLAN, netværkssegmenter.
                - Generelle connectivity-problemer der ikke skyldes bruger- eller klientfejl.

                SECURITY
                Vælg denne når ticketen omhandler sikkerhed:
                - Antivirus/EDR: alerts, karantæne, fundne trusler.
                - Security alerts fra SOC eller andre sikkerhedssystemer.
                - Phishing: mistænkelige mails, rapporteret phishing, "jeg klikkede på et link".
                - Kompromitterede konti, uautoriseret adgang, MFA-sikkerhed.
                - Overtrædelse af sikkerhedspolitikker.
                - Alle tickets markeret som potentielle sikkerhedsrisici.

                ERP
                Vælg denne når ticketen involverer ERP-systemer eller relaterede forretningsprocesser:
                - Microsoft Business Central, Navision, SAP, AX, Dynamics 365 Finance & Operations.
                - Moduler såsom: finans, økonomi, bogholderi, indkøb, lager/styring, produktion, fakturering.
                - Datafejl, bogføringsfejl, manglende posteringer, ERP-integrationer.
                - ERP-specifik adgang og rettighedsproblemer.

                CRM
                Vælg denne når ticketen omhandler:
                - CRM-systemer (Salesforce, Dynamics CRM, HubSpot m.fl.).
                - Kundestyring, salgs-pipeline, leads, kontakter.
                - CRM-integrationer til andre systemer.
                - Fejl i CRM-processer, workflows, dashboards.

                Hvis ticketen IKKE passer tydeligt i nogen kategori:
                Svar med DEFAULT

                Dette inkluderer:
                - Uklar tekst, ingen reel kontekst.
                - Ikke-IT relaterede henvendelser.
                - Indhold du ikke kan klassificere ud fra reglerne ovenfor.
                - Tickets der potentielt kunne høre til flere afdelinger, men hvor ingen kategori er klar → vælg DEFAULT.

                ABSOLUTTE REGLER:
                1. Svar KUN med nøgleordet.
                   Eksempel: SERVICE_DESK_L1
                2. Ingen forklaringer, ingen tekst, ingen tegn ud over selve svaret.
                3. Ingen variationer eller antagelser – vælg den mest logiske afdeling ud fra beskrivelsen.
                4. Hvis flere kategorier kunne passe, men én er tydeligt mest sandsynlig → vælg dén.
                5. Hvis ingen kategori passer tydeligt → DEFAULT.
                6. Undlad al neutral tekst som: "Mit svar er", "Jeg vælger", "Afdeling:" osv.
                7. Svaret må aldrig indeholde mellemrum, punktum eller ekstra ord.

                Nedenfor får du ticket-data. Brug reglerne ovenfor til at vælge præcis én afdeling.

                Ticket subject:
                %s

                Ticket body:
                %s
                """.formatted(subject, body);
    }

    /**
     * Finder priority-navn (SIMA, P1, P2, P3)
     */
    public String routePriority(String subject, String body) {
        String prompt = buildPromptPriority(subject, body);

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

    private String buildPromptPriority(String subject, String body) {
        return """
                Du fungerer som en deterministisk prioriterings-motor for IT-supporttickets hos itm8.
                Dit eneste ansvar er at analysere en tickets subject + body og vælge præcis én prioritet.

                Du må aldrig give forklaringer, begrundelser, fritekst, ekstra ord, synonymer eller formatering.
                Du må kun svare med én af de prædefinerede nøgleord nedenfor: SIMA, P1, P2 eller P3. Ingen undtagelser.

                PRIORITETER (skrevet fra højest til lavest):

                SIMA
                Brug SIMA når:
                - Der er tale om kritiske overvågnings-/systemalerts, typisk fra monitoring eller automatik:
                  HOST DOWN, CRITICAL ALERT, SERVICE NOT RESPONDING, FILESYSTEM CRITICAL, CPU 100, MEMORY CRITICAL.
                - Infrastruktur- eller host-niveau hændelser der indikerer umiddelbar risiko for større nedbrud eller datatab.
                - Overvågning eller systemer eksplicit markerer hændelsen som "critical" på host/service-niveau.
                SIMA er reserveret til de mest kritiske tekniske alerts, typisk fra systemer – ikke fra enkelte brugere.

                P1
                Brug P1 når:
                - Servere, systemer eller tjenester er nede for kunden/brugerne:
                  SERVER DOWN, SYSTEM DOWN, OUTAGE, OFFLINE, NETWORK DOWN, INTERNET DOWN, VPN DOWN.
                - Kritiske forretningssystemer ikke er tilgængelige for mange brugere / hele lokationer.
                - Der nævnes ord som: EMERGENCY, CRITICAL ALERT, HIGH SEVERITY, SEV1 (hvis ikke allerede SIMA).
                - Alvorlige infrastrukturproblemer: STORAGE FAILURE, HOST DOWN, ESXI HOST OFFLINE, POWER OUTAGE,
                  BACKUP FAILURE, DATABASE DOWN, AUTHENTICATION SERVICE DOWN, DNS FAILURE.
                - Mange brugere er påvirket, og forretningen er væsentligt påvirket her og nu.

                Hvis både SIMA- og P1-sprogbrug optræder:
                - Hvis det ligner overvågnings-/host-alerts -> SIMA.
                - Hvis fokus er forretningspåvirkning og brugeroplevelse -> P1.

                P2
                Brug P2 når:
                - Tjenester er TILGÆNGELIGE, men påvirket:
                  SLOW PERFORMANCE, HIGH CPU, HIGH MEMORY, NETWORK UNSTABLE, PACKET LOSS, LATENCY.
                - Der er performance- eller stabilitetsproblemer, men systemerne er ikke helt nede.
                - BACKUP WARNING eller andre warnings (ikke failure).
                - VM FREEZE, HYPER-V ISSUE, DEGRADE PERFORMANCE, DISK WARNING, FAILS OCCASIONALLY.
                - Flere brugere kan være påvirket, men der findes typisk workarounds, og der er ingen fuld outage.

                P3
                Brug P3 når:
                - Der er tale om almindelige incidents eller service requests:
                  RDP ISSUE, VPN NOT WORKING (for enkelte brugere), PRINTER NOT WORKING, CANNOT LOGIN.
                - Standardanmodninger:
                  UPDATE NEEDED, MAINTENANCE, SMALL ERROR, CONFIGURATION CHANGE, GROUP POLICY REQUEST,
                  CREATE USER, RESET PASSWORD.
                - Enkeltbruger- eller low-impact-problemer uden stor forretningskritisk påvirkning.
                - Generelle, planlagte eller mindre ændringer.

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
                """.formatted(subject, body);
    }
}
