package com.example.ticketservice.controller;

import com.example.ticketservice.dto.RoutingStatsDepartmentDTO;
import com.example.ticketservice.dto.RoutingStatsPriorityDTO;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.service.DepartmentService;
import com.example.ticketservice.service.MetricsService;
import com.example.ticketservice.service.PriorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticketservice")
@CrossOrigin(origins = "http://localhost:63342") // <- din frontend origin
public class Controller {

    private final MetricsService metricsService;
    private final DepartmentService departmentService;
    private final PriorityService priorityService;

    public Controller(MetricsService metricsService,
                      DepartmentService departmentService,
                      PriorityService priorityService) {
        this.metricsService = metricsService;
        this.departmentService = departmentService;
        this.priorityService = priorityService;
    }

    // ===== ROUTING STATS =====

    @GetMapping("/stats")
    public RoutingStatsDepartmentDTO getRoutingStatsDepartments() {
        return metricsService.getRoutingStatsDepartments();
    }

    @GetMapping("/stats/{id}")
    public RoutingStatsDepartmentDTO getRoutingStatsOneDepartment(@PathVariable int id) {
        return metricsService.getRoutingStatsOneDepartment(id);
    }

    @GetMapping("/stats/priorities")
    public RoutingStatsPriorityDTO getRoutingStatsPriorities() {
        return metricsService.getRoutingStatsPriorities();
    }

    @GetMapping("/stats/priorities/{id}")
    public RoutingStatsPriorityDTO getRoutingStatsOnePriority(@PathVariable int id) {
        return metricsService.getRoutingStatsOnePriority(id);
    }

    // ===== METRICS =====

    @GetMapping("/metrics/departments/{id}")
    public ResponseEntity<?> getMetricsDepartment(@PathVariable int id){
        try {
            return ResponseEntity.ok(metricsService.getMetricsDepartment(id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("departments/tickets/{id}")
    public ResponseEntity<?> getMetricsDepartmentForDepartment(@PathVariable int id){
        try {
            return ResponseEntity.ok(metricsService.getMetricsDepartmentsForDepartment(id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/metrics/priorities/{id}")
    public ResponseEntity<?> getMetricsPriorities(@PathVariable int id){
        try {
            return ResponseEntity.ok(metricsService.getMetricsPriority(id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ===== DEPARTMENTS =====

    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable int id){
        try {
            return ResponseEntity.ok(departmentService.getDepartment(id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // NY: opret department med POST /departments
    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        return ResponseEntity.ok(createdDepartment);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, department);
            return ResponseEntity.ok(updatedDepartment);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok("Department deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ===== PRIORITIES =====

    @GetMapping("/priorities")
    public List<Priority> getPriorities() {
        return priorityService.getPriorities();
    }

    @GetMapping("/priorities/{id}")
    public ResponseEntity<?> getPriority(@PathVariable int id){
        try {
            return ResponseEntity.ok(priorityService.getPriority(id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/priorities")
    public ResponseEntity<Priority> createPriority(@RequestBody Priority priority) {
        Priority createdPriority = priorityService.createPriority(priority);
        return ResponseEntity.ok(createdPriority);
    }

    @PutMapping("/priorities/{id}")
    public ResponseEntity<?> updatePriority(@PathVariable int id, @RequestBody Priority priority) {
        try {
            Priority updatedPriority = priorityService.updatePriority(id, priority);
            return ResponseEntity.ok(updatedPriority);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/priorities/{id}")
    public ResponseEntity<?> deletePriority(@PathVariable int id) {
        try {
            priorityService.deletePriority(id);
            return ResponseEntity.ok("Priority deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
