package com.example.ticketservice.service;

import com.example.ticketservice.repository.DepartmentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {


    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;



    @Test
    void isValidEmail() {
        //Arrange

        String mail = "asdhjasdhjsad@mail.com";
        String mail1 = "asdasdasdasdasd";
        String mail2 = "hello";
        String mail3 = "mail@mail.com";

        //Act
        assertTrue(departmentService.isValidEmail(mail));
        assertFalse(departmentService.isValidEmail(mail1));
        assertFalse(departmentService.isValidEmail(mail2));
        assertTrue(departmentService.isValidEmail(mail3));


    }
}
