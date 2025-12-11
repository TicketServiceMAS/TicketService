package com.example.ticketservice.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DepartmentTest {

    @Test
    void allArgsConstructorSetsAllFields() {
        List<MetricsDepartment> metricsDepartments = new ArrayList<>();

        Department department = new Department(
                1,
                "SERVICE_DESK_L1",
                "l1@example.com",
                metricsDepartments
        );

        assertThat(department.getDepartmentID()).isEqualTo(1);
        assertThat(department.getDepartmentName()).isEqualTo("SERVICE_DESK_L1");
        assertThat(department.getMailAddress()).isEqualTo("l1@example.com");
        assertThat(department.getMetricsDepartments()).isSameAs(metricsDepartments);
    }

    @Test
    void settersAndGettersWorkCorrectly() {
        Department department = new Department();

        department.setDepartmentID(2);
        department.setDepartmentName("NETWORK");
        department.setMailAddress("network@example.com");

        assertThat(department.getDepartmentID()).isEqualTo(2);
        assertThat(department.getDepartmentName()).isEqualTo("NETWORK");
        assertThat(department.getMailAddress()).isEqualTo("network@example.com");
    }
}
