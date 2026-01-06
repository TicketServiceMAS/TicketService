package com.example.ticketservice.controller;

import com.example.ticketservice.dto.RoutingStatsDepartmentDTO;
import com.example.ticketservice.dto.UserDTO;
import com.example.ticketservice.entity.Department;
import com.example.ticketservice.entity.User;
import com.example.ticketservice.service.CustomUserDetailsService;
import com.example.ticketservice.service.JwtService;
import com.example.ticketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public UserDTO getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable int id,
            @RequestBody User user
    ) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        UserDetails user = customUserDetailsService.loadUserByUsername(request.getUsername());
        String jwt = jwtService.generateToken(user);

        return ResponseEntity.ok(jwt);
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

class AuthRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
