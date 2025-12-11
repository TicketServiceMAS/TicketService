package com.example.ticketservice.entity;

import com.example.ticketservice.repository.DepartmentRepository;
import com.example.ticketservice.repository.MetricsDepartmentRepository;
import com.example.ticketservice.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DepartmentIntegrationTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MetricsDepartmentRepository metricsDepartmentRepository;

    @BeforeEach
    void cleanDatabase() {
        metricsDepartmentRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    void saveAndLoadDepartmentPersistsFields() {
        Department department = new Department(
                0,
                "SERVICE_DESK_L1",
                "l1@example.com",
                null
        );

        Department saved = departmentRepository.save(department);

        assertThat(saved.getDepartmentID()).isNotZero();

        Department found = departmentRepository.findById(saved.getDepartmentID())
                .orElseThrow();

        assertThat(found.getDepartmentID()).isEqualTo(saved.getDepartmentID());
        assertThat(found.getDepartmentName()).isEqualTo("SERVICE_DESK_L1");
        assertThat(found.getMailAddress()).isEqualTo("l1@example.com");
    }

    @Test
    void departmentCanBeLinkedWithMetricsDepartments() {
        // gem department
        Department department = new Department(
                0,
                "NETWORK",
                "network@example.com",
                null
        );
        Department savedDepartment = departmentRepository.save(department);

        // gem to metrics for dette department
        MetricsDepartment m1 = new MetricsDepartment(
                0,
                "Subject 1",
                Status.SUCCESS,
                LocalDate.of(2024, 5, 1),
                savedDepartment
        );
        MetricsDepartment m2 = new MetricsDepartment(
                0,
                "Subject 2",
                Status.FAILURE,
                LocalDate.of(2024, 5, 2),
                savedDepartment
        );

        metricsDepartmentRepository.saveAll(List.of(m1, m2));

        // hent alle metrics og filtrér på department
        List<MetricsDepartment> metricsForDept = metricsDepartmentRepository.findAll()
                .stream()
                .filter(m -> m.getDepartment().getDepartmentID() == savedDepartment.getDepartmentID())
                .collect(Collectors.toList());

        assertThat(metricsForDept).hasSize(2);
    }
}
