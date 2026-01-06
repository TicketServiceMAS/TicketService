package com.example.ticketservice.config;

import com.example.ticketservice.entity.*;
import com.example.ticketservice.repository.*;
import com.example.ticketservice.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

    @Autowired
    MetricsRepository metricsRepository;

    @Autowired
    UserRepository userRepository;



    @Override
    public void run(String... args) throws Exception{

        if (departmentRepository.findAll().isEmpty()) {
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

        if (priorityRepository.findAll().isEmpty()) {
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

            // L1 – P3

if (metricsRepository.findAll().isEmpty()) {
    Metrics mL1P3 = new Metrics();
    mL1P3.setSubject("INC-40001 supermail@mail.com L1 password reset P3");
    mL1P3.setContent("I WANT MY PASSWORD RESESTT!!!!");
    mL1P3.setDate(LocalDate.of(2025, 11, 1));

    MetricsDepartment mdL1P3 = new MetricsDepartment();
    mdL1P3.setStatus(Status.DEFAULTED);
    mdL1P3.setDepartment(department);

    MetricsPriority mpL1P3 = new MetricsPriority();
    mpL1P3.setStatus(Status.SUCCESS);
    mpL1P3.setPriority(priority2);

    mL1P3.setMetricsDepartment(mdL1P3);
    mL1P3.setMetricsPriority(mpL1P3);

    metricsRepository.save(mL1P3);


    Metrics mL1P2 = new Metrics();
    mL1P2.setSubject("INC-40002 megamail@email.com L1 account unlock P2");
    mL1P2.setContent("unlock account pls");
    mL1P2.setDate(LocalDate.of(2025, 11, 2));

    MetricsDepartment mdL1P2 = new MetricsDepartment();
    mdL1P2.setStatus(Status.FAILURE);
    mdL1P2.setDepartment(department);

    MetricsPriority mpL1P2 = new MetricsPriority();
    mpL1P2.setStatus(Status.SUCCESS);
    mpL1P2.setPriority(priority1);

    mL1P2.setMetricsDepartment(mdL1P2);
    mL1P2.setMetricsPriority(mpL1P2);

    metricsRepository.save(mL1P2);

    Metrics mL2P2 = new Metrics();
    mL2P2.setSubject("INC-40003 ultramail@mail.com L2 escalation P2");
    mL2P2.setContent(":(");
    mL2P2.setDate(LocalDate.of(2025, 11, 3));

    MetricsDepartment mdL2P2 = new MetricsDepartment();
    mdL2P2.setStatus(Status.DEFAULTED);
    mdL2P2.setDepartment(department1);

    MetricsPriority mpL2P2 = new MetricsPriority();
    mpL2P2.setStatus(Status.SUCCESS);
    mpL2P2.setPriority(priority1);

    mL2P2.setMetricsDepartment(mdL2P2);
    mL2P2.setMetricsPriority(mpL2P2);

    metricsRepository.save(mL2P2);

    Metrics mL2P1 = new Metrics();
    mL2P1.setSubject("INC-40004 gmail@hotmail.com L2 outage P1");
    mL2P1.setContent("Det virker ikke. Kæmpe stor outage");
    mL2P1.setDate(LocalDate.of(2025, 11, 4));

    MetricsDepartment mdL2P1 = new MetricsDepartment();
    mdL2P1.setStatus(Status.SUCCESS);
    mdL2P1.setDepartment(department1);

    MetricsPriority mpL2P1 = new MetricsPriority();
    mpL2P1.setStatus(Status.SUCCESS);
    mpL2P1.setPriority(priority);

    mL2P1.setMetricsDepartment(mdL2P1);
    mL2P1.setMetricsPriority(mpL2P1);

    metricsRepository.save(mL2P1);
    Metrics mWpP3 = new Metrics();
    mWpP3.setSubject("INC-40005 mailemail@mail.com Workplace onboarding P3");
    mWpP3.setContent("Jeg skal have lavet 500 powerpoint slides til i morgen. Hjælp mig ");
    mWpP3.setDate(LocalDate.of(2025, 11, 5));

    MetricsDepartment mdWpP3 = new MetricsDepartment();
    mdWpP3.setStatus(Status.SUCCESS);
    mdWpP3.setDepartment(department2);

    MetricsPriority mpWpP3 = new MetricsPriority();
    mpWpP3.setStatus(Status.SUCCESS);
    mpWpP3.setPriority(priority2);

    mWpP3.setMetricsDepartment(mdWpP3);
    mWpP3.setMetricsPriority(mpWpP3);

    metricsRepository.save(mWpP3);
    Metrics mWpP2 = new Metrics();
    mWpP2.setSubject("INC-40006 megamail@mail.com Workplace hardware P2");
    mWpP2.setContent("Jytte fra marketing har brug for en ny laptop. Hun har hældt kaffe på den hun har nu.");
    mWpP2.setDate(LocalDate.of(2025, 11, 6));

    MetricsDepartment mdWpP2 = new MetricsDepartment();
    mdWpP2.setStatus(Status.SUCCESS);
    mdWpP2.setDepartment(department2);

    MetricsPriority mpWpP2 = new MetricsPriority();
    mpWpP2.setStatus(Status.SUCCESS);
    mpWpP2.setPriority(priority1);

    mWpP2.setMetricsDepartment(mdWpP2);
    mWpP2.setMetricsPriority(mpWpP2);

    metricsRepository.save(mWpP2);
    Metrics mNetP1 = new Metrics();
    mNetP1.setSubject("INC-40007 123mail@mail.com Network outage P1");
    mNetP1.setContent("Fix internettet!!!!!!");
    mNetP1.setDate(LocalDate.of(2025, 11, 7));

    MetricsDepartment mdNetP1 = new MetricsDepartment();
    mdNetP1.setStatus(Status.SUCCESS);
    mdNetP1.setDepartment(department3);

    MetricsPriority mpNetP1 = new MetricsPriority();
    mpNetP1.setStatus(Status.SUCCESS);
    mpNetP1.setPriority(priority);

    mNetP1.setMetricsDepartment(mdNetP1);
    mNetP1.setMetricsPriority(mpNetP1);

    metricsRepository.save(mNetP1);
    Metrics mNetP3 = new Metrics();
    mNetP3.setSubject("INC-40008 booh@mail.com Network maintenance P3");
    mNetP3.setDate(LocalDate.of(2025, 11, 8));

    MetricsDepartment mdNetP3 = new MetricsDepartment();
    mdNetP3.setStatus(Status.SUCCESS);
    mdNetP3.setDepartment(department3);

    MetricsPriority mpNetP3 = new MetricsPriority();
    mpNetP3.setStatus(Status.SUCCESS);
    mpNetP3.setPriority(priority2);

    mNetP3.setMetricsDepartment(mdNetP3);
    mNetP3.setMetricsPriority(mpNetP3);

    metricsRepository.save(mNetP3);
}
}

if (userRepository.findAll().isEmpty()) {
    User user = new User();
    user.setDepartment(department2);
    user.setUsername("Userboy");
    user.setPassword(new BCryptPasswordEncoder().encode("password123"));
    user.setAdmin(false);
    userRepository.save(user);

    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword(new BCryptPasswordEncoder().encode("admin123"));
    admin.setAdmin(true);
    userRepository.save(admin);

    User testUser = new User();
    testUser.setUsername("Testuser");
    testUser.setPassword(new BCryptPasswordEncoder().encode("abc"));
    testUser.setAdmin(false);
    userRepository.save(testUser);

    User testUser1 = new User();
    testUser1.setUsername("abc");
    testUser1.setDepartment(department1);
    testUser1.setPassword(new BCryptPasswordEncoder().encode("123"));
    testUser1.setAdmin(false);
    userRepository.save(testUser1);

    // ===== SERVICE_DESK_L1 =====
    User serviceDeskL1 = new User();
    serviceDeskL1.setUsername("servicedesk.l1");
    serviceDeskL1.setDepartment(department);
    serviceDeskL1.setPassword(new BCryptPasswordEncoder().encode("123"));
    serviceDeskL1.setAdmin(false);
    userRepository.save(serviceDeskL1);

// ===== SERVICE_DESK_L2 =====
    User serviceDeskL2 = new User();
    serviceDeskL2.setUsername("servicedesk.l2");
    serviceDeskL2.setDepartment(department1);
    serviceDeskL2.setPassword(new BCryptPasswordEncoder().encode("123"));
    serviceDeskL2.setAdmin(false);
    userRepository.save(serviceDeskL2);

// ===== WORKPLACE =====
    User workplace = new User();
    workplace.setUsername("workplace");
    workplace.setDepartment(department2);
    workplace.setPassword(new BCryptPasswordEncoder().encode("123"));
    workplace.setAdmin(false);
    userRepository.save(workplace);

// ===== NETWORK =====
    User network = new User();
    network.setUsername("network");
    network.setDepartment(department3);
    network.setPassword(new BCryptPasswordEncoder().encode("123"));
    network.setAdmin(false);
    userRepository.save(network);

// ===== SECURITY =====
    User security = new User();
    security.setUsername("security");
    security.setDepartment(department4);
    security.setPassword(new BCryptPasswordEncoder().encode("123"));
    security.setAdmin(false);
    userRepository.save(security);

// ===== ERP =====
    User erp = new User();
    erp.setUsername("erp");
    erp.setDepartment(department5);
    erp.setPassword(new BCryptPasswordEncoder().encode("123"));
    erp.setAdmin(false);
    userRepository.save(erp);

// ===== CRM =====
    User crm = new User();
    crm.setUsername("crm");
    crm.setDepartment(department6);
    crm.setPassword(new BCryptPasswordEncoder().encode("123"));
    crm.setAdmin(false);
    userRepository.save(crm);

// ===== DEFAULTED =====
    User defaulted = new User();
    defaulted.setUsername("defaulted");
    defaulted.setDepartment(department7);
    defaulted.setPassword(new BCryptPasswordEncoder().encode("123"));
    defaulted.setAdmin(false);
    userRepository.save(defaulted);
}
        }
    }
}