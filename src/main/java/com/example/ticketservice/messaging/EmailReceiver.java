package com.example.ticketservice.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.ticketservice.entity.Mail;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailReceiver {

    private static final String HOST = System.getenv("HOST");

    @Value("${mail.username}")
    private String USERNAME;

    @Value("${mail.password}")
    private String APP_PASSWORD;

    public List<Mail> receiveMail() {
        List<Mail> mails = new ArrayList<>();
        // 2. Definer IMAP/IMAPS egenskaberne
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", HOST);
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true"); // Brug SSL for sikker forbindelse

        Store store = null;
        Folder inbox = null;

        try {
            // 3. Opret Session og Forbind til Store
            Session session = Session.getDefaultInstance(properties);
            store = session.getStore("imaps");

            // Brugernavn og App-adgangskode til login
            store.connect(HOST, USERNAME, APP_PASSWORD);

            // 4. Åbn Indbakken (Folder)
            inbox = store.getFolder("INBOX");
            // Åben i READ_WRITE for at kunne markere beskeder som læst/slettet
            inbox.open(Folder.READ_WRITE);

            // 5. Hent beskederne
            Message[] messages = inbox.getMessages();

            // Gå igennem hver besked
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
            if(!message.getFlags().contains(Flags.Flag.SEEN)) //Kigger kun på usete mails
                {


                // Hent selve e-mailens indhold
                String content = getTextFromMessage(message);

                // ********** Din Ticket-Oprettelses Logik **********
                // F.eks.: Ticket ticket = createTicket(message.getSubject(), content, message.getFrom());

                // Marker e-mailen som LÆST efter behandling:
                message.setFlag(Flags.Flag.SEEN, true);

                    Address[] from = message.getFrom();
                    String senderEmail = null;

                    if (from != null && from.length > 0) {
                        InternetAddress ia = (InternetAddress) from[0];
                        senderEmail = ia.getAddress();
                    }


                    String subject =  senderEmail + " " + message.getSubject();
                    mails.add(new Mail(subject, content));
            }}

        } catch (NoSuchProviderException e) {
            System.err.println("IMAPS udbyder ikke fundet: " + e.getMessage());
        } catch (MessagingException e) {
            System.err.println("Fejl i forbindelse til mailserveren: " + e.getMessage());
            // Dette kan inkludere AuthenticationFailedException hvis adgangskoden er forkert
        } catch (Exception e) {
            System.err.println("Generel fejl under læsning: " + e.getMessage());
        } finally {
            // 6. Luk forbindelserne
            try {
                if (inbox != null && inbox.isOpen()) {
                    // False betyder, at vi IKKE sletter beskeder, der er markeret til sletning
                    inbox.close(false);
                }
                if (store != null) {
                    store.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mails;
    }

    // Hjælpefunktion til at udtrække teksten fra Message-objektet (forenklet)
    private static String getTextFromMessage(Message message) throws Exception {
        String result = "";

        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            // Hvis e-mailen har flere dele (f.eks. både HTML og almindelig tekst)
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();

        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);

            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
                break; // Vi tager den første almindelige tekst-del og stopper
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                // Rekursivt kald, hvis en del indeholder en anden multipart
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}
