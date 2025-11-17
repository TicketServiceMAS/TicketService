package service;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class MailReceiver {

    // 1. Erstat med dine login-oplysninger
    private static final String HOST = "imap.gmail.com";
    private static final String USERNAME = "ticketservicemas@gmail.com"; // Din fulde Gmail-adresse
    private static final String APP_PASSWORD = "kicc hfld lpmd iybo"; // Vigtigt!

    public static void main(String[] args) {
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
            System.out.println("Antal nye e-mails fundet: " + messages.length);

            // Gå igennem hver besked
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];

                System.out.println("------------------------------------");
                System.out.println("Besked nr: " + (i + 1));
                System.out.println("Emne: " + message.getSubject());
                System.out.println("Afsender: " + InternetAddress.toString(message.getFrom()));
                System.out.println("Dato: " + message.getSentDate());

                // Hent selve e-mailens indhold
                String content = getTextFromMessage(message);
                System.out.println("Indhold (uddrag): " + content.substring(0, Math.min(content.length(), 200)) + "...");

                // ********** Din Ticket-Oprettelses Logik **********
                // F.eks.: Ticket ticket = createTicket(message.getSubject(), content, message.getFrom());

                // Marker e-mailen som LÆST efter behandling:
                message.setFlag(Flags.Flag.SEEN, true);
            }

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
