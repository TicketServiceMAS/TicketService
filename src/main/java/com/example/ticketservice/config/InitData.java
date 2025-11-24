package com.example.ticketservice.config;

import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.repository.PriorityRepository;
import com.example.ticketservice.util.DepartmentName;
import com.example.ticketservice.util.PriorityName;
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

    @Override
    public void run(String... args) throws Exception{
        Department department = new Department();
        department.setDepartmentName(DepartmentName.SERVICE_DESK_L1);
        department.setMailAddress("agnethe.cpf@gmail.com");
        departmentRepository.save(department);

        Department department1 = new Department();
        department1.setDepartmentName(DepartmentName.SERVICE_DESK_L2);
        department1.setMailAddress("agnethe.cpf@gmail.com");
        departmentRepository.save(department1);

        Department department2 = new Department();
        department2.setDepartmentName(DepartmentName.WORKPLACE);
        department2.setMailAddress("agnethe.cpf@gmail.com");
        departmentRepository.save(department2);

        Department department3 = new Department();
        department3.setDepartmentName(DepartmentName.NETWORK);
        department3.setMailAddress("agnethe.cpf@gmail.com");
        departmentRepository.save(department3);

        Department department4 = new Department();
        department4.setDepartmentName(DepartmentName.SECURITY);
        department4.setMailAddress("agnethe.cpf@gmail.com");
        departmentRepository.save(department4);

        Department department5 = new Department();
        department5.setDepartmentName(DepartmentName.ERP);
        department5.setMailAddress("agnethe.cpf@gmail.com");
        departmentRepository.save(department5);

        Department department6 = new Department();
        department6.setDepartmentName(DepartmentName.CRM);
        department6.setMailAddress("agnethe.cpf@gmail.com");
        departmentRepository.save(department6);

        Department department7 = new Department();
        department7.setDepartmentName(DepartmentName.UNKNOWN);
        department7.setMailAddress("agnethe.cpf@gmail.com");
        departmentRepository.save(department7);

        Priority priority = new Priority();
        priority.setPriorityName(PriorityName.P1);
        priorityRepository.save(priority);

        Priority priority1 = new Priority();
        priority1.setPriorityName(PriorityName.P2);
        priorityRepository.save(priority1);

        Priority priority2 = new Priority();
        priority2.setPriorityName(PriorityName.P3);
        priorityRepository.save(priority2);

        Priority priority3 = new Priority();
        priority3.setPriorityName(PriorityName.SIMA);
        priorityRepository.save(priority3);

    }
}
