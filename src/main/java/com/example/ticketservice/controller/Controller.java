package com.example.ticketservice.controller;

import com.example.ticketservice.entity.MetricsDepartment;
import com.example.ticketservice.entity.MetricsPriority;
import com.example.ticketservice.service.EmailReceiver;
import com.example.ticketservice.service.EmailSender;
import com.example.ticketservice.service.MetricsService;
import com.example.ticketservice.service.TicketRouter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/ticketservice")
@RestController
@CrossOrigin
public class Controller {
    private final EmailReceiver emailReceiver;
    private final EmailSender emailSender;
    private final MetricsService metricsService;
    private final TicketRouter ticketRouter;

    public Controller(EmailReceiver emailReceiver, EmailSender emailSender, MetricsService metricsService, TicketRouter ticketRouter){
        this.emailReceiver = emailReceiver;
        this.emailSender = emailSender;
        this.metricsService = metricsService;
        this.ticketRouter = ticketRouter;
    }

    @GetMapping("/departments")
    public List<MetricsDepartment> getMetricsDepartments(){
        return metricsService.getAllMetricsDepartments();
    }

    @GetMapping("/priorities")
    public List<MetricsPriority> getMetricsPriorities(){
        return metricsService.getAllMetricsPriorities();
    }
}
