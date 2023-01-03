package ru.java.securityjwt.service;


import org.springframework.http.ResponseEntity;
import ru.java.securityjwt.dto.UserDto;
import ru.java.securityjwt.entity.User;

public interface UserService {
    ResponseEntity<?> getAllUsers() throws Exception;

    ResponseEntity<?> addUser(User user) throws Exception;

    ResponseEntity<?> findById(Long id) throws Exception;

    ResponseEntity<?> updateUser(User user) throws Exception;

    ResponseEntity<?> login(UserDto user) throws Exception;

    ResponseEntity<?> deleteById(Long id) throws Exception;
}
