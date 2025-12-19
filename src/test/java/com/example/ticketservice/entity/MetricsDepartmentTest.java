/*package com.example.ticketservice.entity;

import com.example.ticketservice.util.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MetricsDepartmentTest {

    @Test
    void allArgsConstructorSetsAllFieldsCorrectly() {
        Department department = new Department(
                1,
                "SERVICE_DESK_L1",
                "l1@example.com",
                null
        );

        LocalDate date = LocalDate.of(2024, 5, 5);

        MetricsDepartment metrics = new MetricsDepartment(
                10,             // metricsDepartmentID
                "Subject",      // subject
                Status.SUCCESS, // status
                date,           // date
                department      // department
        );

        assertThat(metrics.getMetricsDepartmentID()).isEqualTo(10);
        assertThat(metrics.getSubject()).isEqualTo("Subject");
        assertThat(metrics.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(metrics.getDate()).isEqualTo(date);
        assertThat(metrics.getDepartment()).isEqualTo(department);
    }

    @Test
    void settersAndGettersWorkCorrectly() {
        MetricsDepartment metrics = new MetricsDepartment();

        Department department = new Department(
                2,
                "NETWORK",
                "network@example.com",
                null
        );

        LocalDate date = LocalDate.of(2024, 6, 1);

        metrics.setMetricsDepartmentID(5);
        metrics.setSubject("Test subject");
        metrics.setStatus(Status.FAILURE);
        metrics.setDate(date);
        metrics.setDepartment(department);

        assertThat(metrics.getMetricsDepartmentID()).isEqualTo(5);
        assertThat(metrics.getSubject()).isEqualTo("Test subject");
        assertThat(metrics.getStatus()).isEqualTo(Status.FAILURE);
        assertThat(metrics.getDate()).isEqualTo(date);
        assertThat(metrics.getDepartment()).isEqualTo(department);
    }

    @Test
    void ensureDateIsSetSetsDateWhenNull() {
        MetricsDepartment metrics = new MetricsDepartment();
        metrics.setDate(null);

        metrics.ensureDateIsSet();

        LocalDate today = LocalDate.now();
        assertThat(metrics.getDate()).isNotNull();
        // lidt robusthed ift. dato (skulle være i dag)
        assertThat(metrics.getDate()).isBetween(today.minusDays(1), today.plusDays(1));
    }

    @Test
    void ensureDateIsSetDoesNotOverrideExistingDate() {
        LocalDate originalDate = LocalDate.of(2023, 12, 24);
        MetricsDepartment metrics = new MetricsDepartment();
        metrics.setDate(originalDate);

        metrics.ensureDateIsSet();

        assertThat(metrics.getDate()).isEqualTo(originalDate);
    }
}*/
