package ru.java.securityjwt.service;


import ru.java.securityjwt.dto.UserDto;
import ru.java.securityjwt.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    void save(User user);

    User findById(Long id);

    void update(User user);

    String login(UserDto user);

    void delete(Long id);
}
