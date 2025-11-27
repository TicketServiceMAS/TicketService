package com.example.ticketservice.service;

import com.example.ticketservice.entity.MetricsDepartment;
import com.example.ticketservice.entity.MetricsPriority;
import com.example.ticketservice.repository.MetricsDepartmentRepository;
import com.example.ticketservice.repository.MetricsPriorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricsService {
    private MetricsDepartmentRepository metricsDepartmentRepository;
    private MetricsPriorityRepository metricsPriorityRepository;

    public MetricsService(MetricsDepartmentRepository metricsDepartmentRepository, MetricsPriorityRepository metricsPriorityRepository){
        this.metricsDepartmentRepository = metricsDepartmentRepository;
        this.metricsPriorityRepository = metricsPriorityRepository;
    }

    public List<MetricsDepartment> getAllMetricsDepartments(){
        return metricsDepartmentRepository.findAll();
    }

    public List<MetricsPriority> getAllMetricsPriorities(){
        return metricsPriorityRepository.findAll();
    }
}
