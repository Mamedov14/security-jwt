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

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto user) {
        return ResponseEntity.ok(service.login(user));
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> save(@Valid @RequestBody User user) {
        service.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> update(@RequestBody User user) {
        service.update(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/getuser/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/user/getAllUsers")
    public List<User> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/user/deleteById/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
	

