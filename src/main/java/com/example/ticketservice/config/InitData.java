package com.example.ticketservice.config;

import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.MetricsDepartment;
import com.example.ticketservice.entity.MetricsPriority;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.repository.MetricsDepartmentRepository;
import com.example.ticketservice.repository.MetricsPriorityRepository;
import com.example.ticketservice.repository.PriorityRepository;
import com.example.ticketservice.util.DepartmentName;
import com.example.ticketservice.util.PriorityName;
import com.example.ticketservice.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ticketservice.repository.DepartmentRepository;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    PriorityRepository priorityRepository;

    @Autowired
    MetricsDepartmentRepository metricsDepartmentRepository;

    @Autowired
    MetricsPriorityRepository metricsPriorityRepository;

    @Override
    public void run(String... args) throws Exception{
        Department department = new Department();
        department.setDepartmentName("SERVICE_DESK_L1");
        department.setMailAddress("masi0001@stud.ek.dk");
        departmentRepository.save(department);

        Department department1 = new Department();
        department1.setDepartmentName("SERVICE_DESK_L2");
        department1.setMailAddress("masi0001@stud.ek.dk");
        departmentRepository.save(department1);

        Department department2 = new Department();
        department2.setDepartmentName("WORKPLACE");
        department2.setMailAddress("masi0001@stud.ek.dk");
        departmentRepository.save(department2);

        Department department3 = new Department();
        department3.setDepartmentName("NETWORK");
        department3.setMailAddress("sarahkiilm1310@gmail.com");
        departmentRepository.save(department3);

        Department department4 = new Department();
        department4.setDepartmentName("SECURITY");
        department4.setMailAddress("sarahkiilm1310@gmail.com");
        departmentRepository.save(department4);

        Department department5 = new Department();
        department5.setDepartmentName("ERP");
        department5.setMailAddress("sarahkiilm1310@gmail.com");
        departmentRepository.save(department5);

        Department department6 = new Department();
        department6.setDepartmentName("CRM");
        department6.setMailAddress("sarahkiilm1310@gmail.com");
        departmentRepository.save(department6);

        Department department7 = new Department();
        department7.setDepartmentName("DEFAULTED");
        department7.setMailAddress("sarahkiilm1310@gmail.com");
        departmentRepository.save(department7);

        Priority priority = new Priority();
        priority.setPriorityName("P1");
        priorityRepository.save(priority);

        Priority priority1 = new Priority();
        priority1.setPriorityName("P2");
        priorityRepository.save(priority1);

        Priority priority2 = new Priority();
        priority2.setPriorityName("P3");
        priorityRepository.save(priority2);

        Priority priority3 = new Priority();
        priority3.setPriorityName("SIMA");
        priorityRepository.save(priority3);

        MetricsDepartment metricsDepartment = new MetricsDepartment();
        metricsDepartment.setStatus(Status.SUCCESS);
        metricsDepartment.setSubject("12345a Help ERP P2");
        metricsDepartmentRepository.save(metricsDepartment);

        MetricsDepartment metricsDepartment1 = new MetricsDepartment();
        metricsDepartment1.setStatus(Status.SUCCESS);
        metricsDepartment1.setSubject("12a HELLO ERP P4");
        metricsDepartmentRepository.save(metricsDepartment1);

        MetricsDepartment metricsDepartment2 = new MetricsDepartment();
        metricsDepartment2.setStatus(Status.FAILURE);
        metricsDepartment2.setSubject("12a HELLO ERP P4");
        metricsDepartmentRepository.save(metricsDepartment2);

        MetricsDepartment metricsDepartment3 = new MetricsDepartment();
        metricsDepartment3.setStatus(Status.DEFAULTED);
        metricsDepartment3.setSubject("12a HELLO ERP P4");
        metricsDepartmentRepository.save(metricsDepartment3);

        // --- NYT MetricsDepartment Data starter her ---
        MetricsDepartment md4 = new MetricsDepartment();
        md4.setStatus(Status.SUCCESS);
        md4.setSubject("Problem med login på WORKPLACE P1");
        metricsDepartmentRepository.save(md4);

        MetricsDepartment md5 = new MetricsDepartment();
        md5.setStatus(Status.FAILURE);
        md5.setSubject("Kan ikke tilgå CRM systemet P2");
        metricsDepartmentRepository.save(md5);

        MetricsDepartment md6 = new MetricsDepartment();
        md6.setStatus(Status.DEFAULTED);
        md6.setSubject("Spørgsmål til NETWORK konfiguration P3");
        metricsDepartmentRepository.save(md6);

        MetricsDepartment md7 = new MetricsDepartment();
        md7.setStatus(Status.SUCCESS);
        md7.setSubject("Fejl i SECURITY-opdatering P1");
        metricsDepartmentRepository.save(md7);

        MetricsDepartment md8 = new MetricsDepartment();
        md8.setStatus(Status.FAILURE);
        md8.setSubject("SERVICE_DESK_L1 kan ikke løse P2-sag");
        metricsDepartmentRepository.save(md8);

        MetricsDepartment md9 = new MetricsDepartment();
        md9.setStatus(Status.SUCCESS);
        md9.setSubject("WORKPLACE P2 - Ny brugeropsætning");
        metricsDepartmentRepository.save(md9);
        // --- NYT MetricsDepartment Data slutter her ---

        MetricsPriority metricsPriority = new MetricsPriority();
        metricsPriority.setStatus(Status.SUCCESS);
        metricsPriority.setSubject("12345a Help ERP P2");
        metricsPriorityRepository.save(metricsPriority);

        MetricsPriority metricsPriority1 = new MetricsPriority();
        metricsPriority1.setStatus(Status.SUCCESS);
        metricsPriority1.setSubject("12a HELLO ERP P4");
        metricsPriorityRepository.save(metricsPriority1);

        // --- NYT MetricsPriority Data starter her ---
        MetricsPriority mp2 = new MetricsPriority();
        mp2.setStatus(Status.FAILURE);
        mp2.setSubject("WORKPLACE P1-issue");
        metricsPriorityRepository.save(mp2);

        MetricsPriority mp3 = new MetricsPriority();
        mp3.setStatus(Status.SUCCESS);
        mp3.setSubject("CRM P3 - opdatering");
        metricsPriorityRepository.save(mp3);

        MetricsPriority mp4 = new MetricsPriority();
        mp4.setStatus(Status.DEFAULTED);
        mp4.setSubject("NETWORK P1 nedbrud");
        metricsPriorityRepository.save(mp4);

        MetricsPriority mp5 = new MetricsPriority();
        mp5.setStatus(Status.FAILURE);
        mp5.setSubject("SERVICE_DESK_L2 SIMA-sag");
        metricsPriorityRepository.save(mp5);

        MetricsPriority mp6 = new MetricsPriority();
        mp6.setStatus(Status.SUCCESS);
        mp6.setSubject("ERP P2 fejlmelding");
        metricsPriorityRepository.save(mp6);
        // --- NYT MetricsPriority Data slutter her ---
    }
}