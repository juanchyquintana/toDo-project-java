package com.databaseproject.cinedev.application.services.user;

import com.databaseproject.cinedev.domain.models.base.User;

import java.util.List;

public interface IUserService {
    public User saveUser(User user);

    public void deleteUser(User user);

    public List<User> getAllUsers();

    public User getUserByEmail(String email);

    boolean existsByEmail(String email);
}
