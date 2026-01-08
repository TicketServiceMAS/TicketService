package com.example.ticketservice.messaging;

import java.time.LocalDate;
import java.util.Properties;

import com.example.ticketservice.entity.*;
import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.MetricsDepartmentRepository;
import com.example.ticketservice.repository.MetricsPriorityRepository;
import com.example.ticketservice.repository.MetricsRepository;
import com.example.ticketservice.util.Status;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {


    private MetricsRepository metricsRepository;


    public EmailSender(MetricsRepository metricsRepository){
        this.metricsRepository = metricsRepository;
    }

    @Value("${mail.username}")
    private String SENDER_EMAIL;

    @Value("${mail.password}")
    private String APP_PASSWORD;

    public void sendMail(Mail mail) {
        String RECIPIENT_EMAIL = mail.getDepartment().getMailAddress();
        String newSubject = mail.getID() + " " + mail.getSubject() + " " + mail.getDepartment().getDepartmentName() + " " + mail.getPriority().getPriorityName();

        String content = mail.getContent();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(RECIPIENT_EMAIL));
            message.setSubject(newSubject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mail.getContent(), "text/plain; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);


        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Fejl under afsendelse af e-mail: " + e.getMessage());
        }
        createMetrics(newSubject, mail);
    }

    public void createMetrics(String newSubject, Mail mail){


        MetricsPriority metricsPriority = new MetricsPriority();
        metricsPriority.setStatus(Status.SUCCESS);
        metricsPriority.setPriority(mail.getPriority());

        MetricsDepartment metricsDepartment = new MetricsDepartment();
        metricsDepartment.setStatus(Status.SUCCESS);
        metricsDepartment.setDepartment(mail.getDepartment());
        if (mail.getDepartment().getDepartmentName().equals("DEFAULTED")){
            metricsDepartment.setStatus(Status.DEFAULTED);
        }

        Metrics metrics = new Metrics();
        metrics.setSubject(newSubject);
        metrics.setContent(mail.getContent());
        metrics.setDate(LocalDate.now());
        metrics.setMetricsDepartment(metricsDepartment);
        metrics.setMetricsPriority(metricsPriority);
        metricsRepository.save(metrics);






    }

}
