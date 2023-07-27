package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {
    void deleteUserById(Long id);

    void updateUser(User userUpdate, Long id);

    User findUser(long id);

    void addUser(User user);

    List<User> showAllUser();

    User findByUsername(String name);
}
