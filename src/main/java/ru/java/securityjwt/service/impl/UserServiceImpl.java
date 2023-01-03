package ru.java.securityjwt.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final JwtUtils utils;


    public User validateUser(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }

    @Override
    public String login(UserDto user) {
        User u = validateUser(user.getUsername(), user.getPassword());
        return utils.generateToken(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(UserServiceException.CODE.USER_NOT_FOUND_DATABASE::get);
    }

    @Override
    public void update(User user) {
        repository.save(user);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
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
