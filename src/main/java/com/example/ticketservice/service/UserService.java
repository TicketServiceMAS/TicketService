package com.example.ticketservice.service;

import com.example.ticketservice.dto.UserDTO;
import com.example.ticketservice.repository.UserRepository;
import com.example.ticketservice.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDTO getUser(int id){
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with ID " + id));
        Integer departmentId = null;
        if (user.getDepartment() != null) {
            departmentId = user.getDepartment().getDepartmentID();
        }
        return new UserDTO(departmentId, user.getUsername(), user.isAdmin());
    }

    public UserDTO updateUser(int id, User user){
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with ID " + id));
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setAdmin(user.isAdmin());
        if (user.getDepartment() != null){
            userToUpdate.setDepartment(user.getDepartment());
        }
        userToUpdate.setDepartment(user.getDepartment());
        if (!user.getPassword().isBlank()){
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            userToUpdate.setPassword(hashedPassword);
        }
        userRepository.save(userToUpdate);
        Integer departmentId = null;
        if (userToUpdate.getDepartment() != null) {
            departmentId = userToUpdate.getDepartment().getDepartmentID();
        }
        return new UserDTO(departmentId, user.getUsername(), user.isAdmin());
    }

    public void deleteUser(int id){
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with id " + id));
        userRepository.delete(user);
    }

    public UserDTO createUser(User user){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        Integer departmentId = null;
        if (user.getDepartment() != null) {
            departmentId = user.getDepartment().getDepartmentID();
        }
        return new UserDTO(departmentId, user.getUsername(), user.isAdmin());

    }

}
