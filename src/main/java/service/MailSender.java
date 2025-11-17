package service;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class MailSender {

    // Erstat disse med dine egne oplysninger:
    private static final String SENDER_EMAIL = "ticketservicemas@gmail.com";
    private static final String APP_PASSWORD = "kicc hfld lpmd iybo";
    private static final String RECIPIENT_EMAIL = "agnethe.cpf@gmail.com";

    public static void main(String[] args) {
        // 1. Opsætning af SMTP-egenskaber (Gmail)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // 2. Opret en Session med Autentifikation
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Brug den genererede App-adgangskode her
                return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
            }
        });

        try {
            // 3. Opret Besked (Message)
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(RECIPIENT_EMAIL));
            message.setSubject("Test af automatisk afsendelse fra Java");

            // Opret e-mailens indhold (body)
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent("Dette er en test e-mail sendt fra et Java-program.", "text/plain; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            // 4. Send Besked
            Transport.send(message);

            System.out.println("E-mailen er sendt med succes!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Fejl under afsendelse af e-mail: " + e.getMessage());
        }
    }

}
