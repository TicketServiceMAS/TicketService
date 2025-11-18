package com.example.ticketservice.config;

import com.example.ticketservice.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ticketservice.repository.CategoryRepository;

import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception{
        System.out.println("initdata running");
        Category category = new Category();
        category.setName("IT");
        category.setImportance(0);
        category.setMailAddress("it@gmail.com");
        List<String> keywords = List.of("Hello", "Okay");
        category.setKeywords(keywords);
        categoryRepository.save(category);
    }
}
