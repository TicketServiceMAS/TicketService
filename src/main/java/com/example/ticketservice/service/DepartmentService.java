package com.example.ticketservice.service;

import com.example.ticketservice.entity.Department;
import com.example.ticketservice.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;


    public DepartmentService (DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }



    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(int id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID " + id));
    }

    public Department createDepartment(Department department) {
        if (isValidEmail(department.getMailAddress())){
        return departmentRepository.save(department);}
        return null;
    }

    public Department updateDepartment(int id, Department department){
        Department departmentToUpdate = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID " + id));
        departmentToUpdate.setDepartmentName(department.getDepartmentName());
        departmentToUpdate.setMailAddress(department.getMailAddress());
        return departmentRepository.save(departmentToUpdate);
    }

    public void deleteDepartment(int id){
        Department departmentToDelete = departmentRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Department not found with ID " + id));
        departmentRepository.delete(departmentToDelete);
    }

    public boolean isValidEmail(String email) {
        if (email == null) return false;

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

}
