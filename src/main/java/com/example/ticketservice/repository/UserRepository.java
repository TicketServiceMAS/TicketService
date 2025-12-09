package com.example.ticketservice.repository;
import com.example.ticketservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

}
