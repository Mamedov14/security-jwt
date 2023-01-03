package ru.java.securityjwt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.securityjwt.dto.UserDto;
import ru.java.securityjwt.entity.User;
import ru.java.securityjwt.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto user) throws Exception {
        return service.login(user);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) throws Exception {
        return service.addUser(user);

    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) throws Exception {
        return service.updateUser(user);
    }

    @GetMapping("/user/getuser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping("/user/getAllUsers")
    public ResponseEntity<?> getAllUsers() throws Exception {
        return service.getAllUsers();
    }

    @DeleteMapping("/user/deleteById/{id}")
    public ResponseEntity<?> deleteUserByID(@PathVariable Long id) throws Exception {
        return service.deleteById(id);
    }
}
	

