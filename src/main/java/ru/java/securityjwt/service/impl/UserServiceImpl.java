package ru.java.securityjwt.service.impl;

import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.java.securityjwt.dto.UserDto;
import ru.java.securityjwt.entity.User;
import ru.java.securityjwt.exception.UserServiceException;
import ru.java.securityjwt.repository.UserRepository;
import ru.java.securityjwt.service.UserService;
import ru.java.securityjwt.utils.JwtUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Map<String, String> map = new HashMap<>();
    private final UserRepository repository;
    private final JwtUtils utils;


    public User validateUser(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }

    @Override
    public ResponseEntity<?> login(UserDto user) throws Exception {
        User u = validateUser(user.getUsername(), user.getPassword());
        if (u != null) {
            String jwtToken = utils.generateToken(user);
            map.put("message", "User successfully logged in");
            map.put("userId", Long.toString(u.getId()));
            map.put("token", jwtToken);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            throw new ServletException("Invalid credentials");
        }
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        List<User> userList = repository.findAll();
        if (userList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(userList);
        }
        map.put("message", "No users Exist");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
    }

    @Override
    public ResponseEntity<?> addUser(User user) {
        User userExist = repository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        if (userExist != null) {
            map.put("message", "Try with different Username and Password");
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } else {

            User userAdded = repository.saveAndFlush(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userAdded);
        }

    }


    @Override
    public ResponseEntity<?> findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(UserServiceException.CODE.USER_NOT_FOUND_DATABASE::get);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        map.put("message", "User doesn't exist");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @Override
    public ResponseEntity<?> updateUser(User user) {
        User existUser = repository.findById(user.getId())
                .orElseThrow(UserServiceException.CODE.USER_NOT_FOUND_DATABASE::get);
        if (existUser != null) {
            User updatedUser = repository.save(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>("User Profile update failed", HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        User userExist = repository.findById(id)
                .orElseThrow(UserServiceException.CODE.USER_NOT_FOUND_DATABASE::get);
        if (userExist != null) {
            repository.deleteById(id);
            map.put("message", "User Deleted Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(map);
        }
        map.put("message", "User doesn't Exist");
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    public UserDetails getByUsername(String username) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(authority);
        User user = repository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                updatedAuthorities);
    }
}
