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

import java.time.LocalDate;
import java.util.Date;

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
        department.setMailAddress("ServiceDeskL1@proton.me");
        departmentRepository.save(department);

        Department department1 = new Department();
        department1.setDepartmentName("SERVICE_DESK_L2");
        department1.setMailAddress("ServiceDeskL2@proton.me");
        departmentRepository.save(department1);

        Department department2 = new Department();
        department2.setDepartmentName("WORKPLACE");
        department2.setMailAddress("ticketservice.workplace@atomicmail.io");
        departmentRepository.save(department2);

        Department department3 = new Department();
        department3.setDepartmentName("NETWORK");
        department3.setMailAddress("ticketservice.network@atomicmail.io");
        departmentRepository.save(department3);

        Department department4 = new Department();
        department4.setDepartmentName("SECURITY");
        department4.setMailAddress("ticketservice.security@atomicmail.io");
        departmentRepository.save(department4);

        Department department5 = new Department();
        department5.setDepartmentName("ERP");
        department5.setMailAddress("ticketservice.erp@proton.me");
        departmentRepository.save(department5);

        Department department6 = new Department();
        department6.setDepartmentName("CRM");
        department6.setMailAddress("ticketservice.crm@atomicmail.io");
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
        metricsDepartment.setDepartment(department5);
        metricsDepartment.setDate(LocalDate.of(2025,11,15));
        metricsDepartment.setSubject("12345a Help ERP P2");
        metricsDepartmentRepository.save(metricsDepartment);

        MetricsDepartment metricsDepartment1 = new MetricsDepartment();
        metricsDepartment1.setStatus(Status.SUCCESS);
        metricsDepartment1.setDepartment(department5);
        metricsDepartment1.setDate(LocalDate.of(2025,11,17));
        metricsDepartment1.setSubject("12a HELLO ERP P4");
        metricsDepartmentRepository.save(metricsDepartment1);

        MetricsDepartment metricsDepartment2 = new MetricsDepartment();
        metricsDepartment2.setStatus(Status.FAILURE);
        metricsDepartment2.setDepartment(department5);
        metricsDepartment2.setDate(LocalDate.of(2025,11,18));
        metricsDepartment2.setSubject("12a HELLO ERP P4");
        metricsDepartmentRepository.save(metricsDepartment2);

        MetricsDepartment metricsDepartment3 = new MetricsDepartment();
        metricsDepartment3.setStatus(Status.DEFAULTED);
        metricsDepartment3.setDepartment(department5);
        metricsDepartment3.setDate(LocalDate.of(2025,11,22));
        metricsDepartment3.setSubject("12a HELLO ERP P4");
        metricsDepartmentRepository.save(metricsDepartment3);

        MetricsDepartment md4 = new MetricsDepartment();
        md4.setStatus(Status.SUCCESS);
        md4.setDepartment(department2);
        md4.setDate(LocalDate.of(2025,11,13));
        md4.setSubject("Problem med login på WORKPLACE P1");
        metricsDepartmentRepository.save(md4);

        MetricsDepartment md5 = new MetricsDepartment();
        md5.setStatus(Status.FAILURE);
        md5.setDepartment(department2);
        md5.setDate(LocalDate.of(2025,11,24));
        md5.setSubject("WORKPLACE Fejl i printeropsætning P2");
        metricsDepartmentRepository.save(md5);

        MetricsDepartment md6 = new MetricsDepartment();
        md6.setStatus(Status.FAILURE);
        md6.setDepartment(department6);
        md6.setDate(LocalDate.of(2025,11,25));
        md6.setSubject("Kan ikke tilgå CRM systemet P2");
        metricsDepartmentRepository.save(md6);

        MetricsDepartment md7 = new MetricsDepartment();
        md7.setStatus(Status.SUCCESS);
        md7.setDepartment(department6);
        md7.setDate(LocalDate.of(2025,11,25));
        md7.setSubject("CRM: Ny brugeroprettelse P3");
        metricsDepartmentRepository.save(md7);

        MetricsDepartment md8 = new MetricsDepartment();
        md8.setStatus(Status.DEFAULTED);
        md8.setDepartment(department3);
        md8.setDate(LocalDate.of(2025,11,26));
        md8.setSubject("Spørgsmål til NETWORK konfiguration P3");
        metricsDepartmentRepository.save(md8);

        MetricsDepartment md9 = new MetricsDepartment();
        md9.setStatus(Status.SUCCESS);
        md9.setDepartment(department3);
        md9.setDate(LocalDate.of(2025,11,27));
        md9.setSubject("NETWORK: Langsom forbindelse P1");
        metricsDepartmentRepository.save(md9);

        MetricsDepartment md10 = new MetricsDepartment();
        md10.setStatus(Status.SUCCESS);
        md10.setDepartment(department4);
        md10.setDate(LocalDate.of(2025,11,27));
        md10.setSubject("Fejl i SECURITY-opdatering P1");
        metricsDepartmentRepository.save(md10);

        MetricsDepartment md11 = new MetricsDepartment();
        md11.setStatus(Status.FAILURE);
        md11.setDepartment(department4);
        md11.setDate(LocalDate.of(2025,11,28));
        md11.setSubject("SECURITY: Phishing email rapporteret P2");
        metricsDepartmentRepository.save(md11);

        MetricsDepartment md12 = new MetricsDepartment();
        md12.setStatus(Status.FAILURE);
        md12.setDepartment(department);
        md12.setDate(LocalDate.of(2025,11,28));
        md12.setSubject("SERVICE_DESK_L1 kan ikke løse P2-sag");
        metricsDepartmentRepository.save(md12);

        MetricsDepartment md13 = new MetricsDepartment();
        md13.setStatus(Status.SUCCESS);
        md13.setDepartment(department);
        md13.setDate(LocalDate.of(2025,11,29));
        md13.setSubject("SD_L1 Standard henvendelse P3");
        metricsDepartmentRepository.save(md13);

        MetricsDepartment md14 = new MetricsDepartment();
        md14.setStatus(Status.SUCCESS);
        md14.setDepartment(department1);
        md14.setDate(LocalDate.of(2025,11,30));
        md14.setSubject("SD_L2 kompliceret problem P1");
        metricsDepartmentRepository.save(md14);

        MetricsDepartment md15 = new MetricsDepartment();
        md15.setStatus(Status.DEFAULTED);
        md15.setDepartment(department7);
        md15.setDate(LocalDate.of(2025,12,1));
        md15.setSubject("Manglende afdeling/prioritet test");
        metricsDepartmentRepository.save(md15);

        MetricsPriority metricsPriority = new MetricsPriority();
        metricsPriority.setStatus(Status.SUCCESS);
        metricsPriority.setPriority(priority1);
        metricsPriority.setDate(LocalDate.of(2025,11,13));
        metricsPriority.setSubject("12345a Help ERP P2");
        metricsPriorityRepository.save(metricsPriority);

        MetricsPriority metricsPriority1 = new MetricsPriority();
        metricsPriority1.setStatus(Status.SUCCESS);
        metricsPriority1.setPriority(priority2);
        metricsPriority1.setDate(LocalDate.of(2025,11,15));
        metricsPriority1.setSubject("12a HELLO ERP P3");
        metricsPriorityRepository.save(metricsPriority1);

        MetricsPriority mp2 = new MetricsPriority();
        mp2.setStatus(Status.FAILURE);
        mp2.setPriority(priority);
        mp2.setDate(LocalDate.of(2025,11,14));
        mp2.setSubject("WORKPLACE P1-issue kritisk");
        metricsPriorityRepository.save(mp2);

        MetricsPriority mp3 = new MetricsPriority();
        mp3.setStatus(Status.SUCCESS);
        mp3.setPriority(priority);
        mp3.setDate(LocalDate.of(2025,11,16));
        mp3.setSubject("NETWORK P1 nedbrud");
        metricsPriorityRepository.save(mp3);

        MetricsPriority mp4 = new MetricsPriority();
        mp4.setStatus(Status.SUCCESS);
        mp4.setPriority(priority);
        mp4.setDate(LocalDate.of(2025,11,17));
        mp4.setSubject("SECURITY P1 success");
        metricsPriorityRepository.save(mp4);

        MetricsPriority mp5 = new MetricsPriority();
        mp5.setStatus(Status.SUCCESS);
        mp5.setPriority(priority1);
        mp5.setDate(LocalDate.of(2025,11,17));
        mp5.setSubject("ERP P2 fejlmelding");
        metricsPriorityRepository.save(mp5);

        MetricsPriority mp6 = new MetricsPriority();
        mp6.setStatus(Status.FAILURE);
        mp6.setPriority(priority1);
        mp6.setDate(LocalDate.of(2025,11,18));
        mp6.setSubject("SERVICE_DESK_L1 P2 kræver eskalering");
        metricsPriorityRepository.save(mp6);

        MetricsPriority mp7 = new MetricsPriority();
        mp7.setStatus(Status.SUCCESS);
        mp7.setPriority(priority2);
        mp7.setDate(LocalDate.of(2025,11,20));
        mp7.setSubject("CRM P3 - opdatering");
        metricsPriorityRepository.save(mp7);

        MetricsPriority mp8 = new MetricsPriority();
        mp8.setStatus(Status.SUCCESS);
        mp8.setPriority(priority2);
        mp8.setDate(LocalDate.of(2025,11,25));
        mp8.setSubject("WORKPLACE P3 spørgsmål");
        metricsPriorityRepository.save(mp8);

        MetricsPriority mp9 = new MetricsPriority();
        mp9.setStatus(Status.FAILURE);
        mp9.setPriority(priority3);
        mp9.setDate(LocalDate.of(2025,11,26));
        mp9.setSubject("SERVICE_DESK_L2 SIMA-sag");
        metricsPriorityRepository.save(mp9);

        MetricsPriority mp10 = new MetricsPriority();
        mp10.setStatus(Status.SUCCESS);
        mp10.setPriority(priority3);
        mp10.setDate(LocalDate.of(2025,11,27));
        mp10.setSubject("SIMA: Løsning implementeret");
        metricsPriorityRepository.save(mp10);
    }
}