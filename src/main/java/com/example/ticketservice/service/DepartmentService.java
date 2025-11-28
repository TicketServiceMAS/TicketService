package com.example.ticketservice.service;

import com.example.ticketservice.dto.DepartmentDTO;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.MetricsDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService (DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(DepartmentDTO departmentDTO){
        Department department = new Department();
        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setMailAddress(departmentDTO.getMailAddress());
        departmentRepository.save(department);
        return department;
    }

    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }


    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }
}
