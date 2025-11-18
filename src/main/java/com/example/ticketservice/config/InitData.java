package com.example.ticketservice.config;

import com.example.ticketservice.entity.Category;
import com.example.ticketservice.entity.Priority;
import com.example.ticketservice.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ticketservice.repository.CategoryRepository;

import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PriorityRepository priorityRepository;

    @Override
    public void run(String... args) throws Exception{
        Category category = new Category();
        category.setName("Cloud & infrastructure");
        category.setImportance(2);
        category.setMailAddress("clouditm8@gmail.com");
        List<String> keywords = List.of("CLOUD", "INFRASTRUCTURE", "AZURE", "AWS", "VIRTUAL MACHINE", "RESOURCE GROUP", "STORAGE", "BLOB STORAGE", "SERVER", "CLOUD MIGRATION",
        "CLOUD ACCOUNT", "SUBSCRIPTION", "HOSTING", "MICROSOFT", "365", "ONEDRIVE", "SHAREPOINT", "AUTOPILOT", "EXCHANGE ONLINE", "DOMAIN", "VMWARE", "LINUX", "WINDOWS", "BACKUP", "DISK ERROR"
        , "NETWORK", "ROUTER", "SWITCH", "WIFI", "FIREWALL", "VPN", "INTERNET DOWN", "SLOW NETWORK", "PING", "PACKET LOSS");
        category.setKeywords(keywords);
        categoryRepository.save(category);

        Category category1 = new Category();
        category1.setName("Cybersecurity");
        category1.setImportance(0);
        category1.setMailAddress("cyberitm8@gmail.com");
        List<String> keywords1 = List.of("MALWARE", "VIRUS", "TROJAN", "RANSOMWARE", "PHISHING", "SPOOFING", "HACK", "BRUTE FORCE", "ATTACK", "BREACH", "COMPROMISED",
                "INTRUSION", "SUSPICIOUS ACTIVITY", "UNAUTHORIZED ACCESS", "DATA LEAK", "DATA BREACH", "KEYLOGGER", "SUSPICIOUS LOGIN", "STRANGE BEHAVIOR", "EXPLOIT", "VULNERABILITY," +
                        "ZERO-DAY", "DDOS", "BOTNET", "PASSWORD RESET", "LOGGED ACCOUNT", "COMPROMISED ACCOUNT", "SPAM", "FAKE EMAIL", "MFA", "SECURITY ALERT", "BLOCKED USER", "FAILED LOGIN", "MICROSOFT SECURITY ALERT",
                "GDPR", "DLP", "ENCRYPTION", "IS027001", "AUDIT", "RISK ASSESSMENT", "SECURITY INCIDENT");
        category1.setKeywords(keywords1);
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("IT operations");
        category2.setImportance(1);
        category2.setMailAddress("operationsitm8@gmail.com");
        List<String> keywords2 = List.of("DOWNTIME", "SYSTEM DOWN", "SERVICE DOWN", "CRASH", "OUTAGE", "OFFLINE", "NOT RESPONDING", "PERFORMANCE ISSUE", "SLOW SYSTEM", "HIGH CPU",
                "HIGH MEMORY", "OVERLOAD", "ALERT", "DEGRADE PERFORMANCE", "MONITORING", "CHECKMK", "PRTG", "SYSTEM ALERT", "WARNING", "UPTIME", "STATUS CHECK", "MAINTANCE",
                "PATCHING", "REBBOT", "UPDATE", "SQL SERVER", "DATABASE DOWN", "DB CONNECTION ERROR", "TIMEOUT", "DEADLOCK", "SLOW QUERY", "BACKUP FAILED", "SCHEDULED JOB", "TASK SCHEDULER");
        category2.setKeywords(keywords2);
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setName("Application services");
        category3.setImportance(3);
        category3.setMailAddress("appitm8@gmail.com");
        List<String> keywords3 = List.of("APPLICATION ERROR", "APP ERROR", "BUG", "CRASH", "EXCEPTION", "MODULE NOT WORKING", "FEATURE NOT WORKING", "LOADING ISSUE", "CONFIGURATION ERROR",
                "INTEGRATION ERROR", "UPDATE REQUIRED", "NAV", "BUSINESS CENTRAL", "BC", "DYNAMICS", "FINANCE", "OPERATIONS", "INVOICE ISSUE", "CRM", "PIPELINE", "BI", "API",
                "SYNC ERROR", "WEBHOOK", "DATA IMPORT", "SQL QUERY", "MIGRATION", "PERMISSION");
        category3.setKeywords(keywords3);
        categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setName("Digital transformation");
        category4.setImportance(4);
        category4.setMailAddress("digitalitm8@gmail.com");
        List<String> keywords4 = List.of("MODERN WORKPLACE", "SHARE POINT", "ADOPTION", "ONBOARDING", "TRAINING", "COLLABORATION", "WORKSHOP", "RPA", "AI", "COPILOT", "CHATBOT",
                "PROCESS OPTIMIZATION", "WORKFLOW", "DIGITALIZATION", "EFFICIENCY", "MANAGEMENT", "DIGITAL STRATEGY", "GOVERNANCE", "COMPLIANCE", "DATA ARCHITECTURE", "INNOVATION", "PROTOTYPE");
        category4.setKeywords(keywords4);
        categoryRepository.save(category4);

        Priority priority = new Priority();
        priority.setName("sima");
        priority.setImportance(0);
        List<String> priorityKeywords = List.of("HOST DOWN", "CRITICAL ALERT", "SERVICE NOT RESPONDING", "FILESYSTEM CRITICAL", "CPU 100%",
                "MEMORY CRITICAL");
        priority.setKeywords(priorityKeywords);
        priorityRepository.save(priority);


        Priority priority1 = new Priority();
        priority1.setName("p1");
        priority1.setImportance(1);
        List<String> priorityKeywords1 = List.of("SERVER DOWN", "SYSTEM DOWN", "OUTAGE", "OFFLINE", "NETWORK DOWN", "INTERNET DOWN"
        , "VPN DOWN", "CRITICAL ALERT", "EMERGENCY", "HIGH SEVERITY", "STORAGE FAILURE", "HOST DOWN", "ESXI HOST OFFLINE", "POWER OUTAGE", "BACKUP FAILURE",
                "DATABASE DOWN", "AUTHENTICATION SERVICE DOWN", "DNS FAILURE");
        priority1.setKeywords(priorityKeywords1);
        priorityRepository.save(priority1);

        Priority priority2 = new Priority();
        priority2.setName("p2");
        priority2.setImportance(2);
        List<String> priorityKeywords2 = List.of("SLOW PERFORMANCE", "HIGH CPU", "HIGH MEMORY", "NETWORK UNSTABLE", "PACKET LOSS", "LATENCY", "BACKUP WARWNING",
                "VM FREEZE", "HYPER-V ISSUE", "DEGRADE PERFORMANCE", "DISK WARNING", "FAILS OCCASIONALLY");
        priority2.setKeywords(priorityKeywords2);
        priorityRepository.save(priority2);

        Priority priority3 = new Priority();
        priority3.setName("p3");
        priority3.setImportance(3);
        List<String> priorityKeywords3 = List.of("RDP ISSUE", "VPN NOT WORKING", "PRINTER NOT WORKING", "CANNOT LOGIN", "UPDATE NEEDED",
                "MAINTENANCE", "SMALL ERROR", "CONFIGURATION CHANGE", "GROUP POLICY REQUEST", "CREATE USER", "RESET PASSWORD");
        priority3.setKeywords(priorityKeywords3);
        priorityRepository.save(priority3);



    }
}
