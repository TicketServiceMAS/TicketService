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
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", HOST);
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true"); // Brug SSL for sikker forbindelse

        Store store = null;
        Folder inbox = null;

        try {
            Session session = Session.getDefaultInstance(properties);
            store = session.getStore("imaps");

            store.connect(HOST, USERNAME, APP_PASSWORD);

            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.getMessages();

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
            if(!message.getFlags().contains(Flags.Flag.SEEN)) //Kigger kun på usete mails
                {



                String content = getTextFromMessage(message);

                // ********** Din Ticket-Oprettelses Logik **********
                // F.eks.: Ticket ticket = createTicket(message.getSubject(), content, message.getFrom());


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
        } catch (Exception e) {
            System.err.println("Generel fejl under læsning: " + e.getMessage());
        } finally {
            try {
                if (inbox != null && inbox.isOpen()) {
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

    private static String getTextFromMessage(Message message) throws Exception {
        String result = "";

        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
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
                break;
            } else if (bodyPart.getContent() instanceof MimeMultipart) {

                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}
