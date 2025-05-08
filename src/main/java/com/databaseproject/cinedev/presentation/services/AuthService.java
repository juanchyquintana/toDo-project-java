package com.databaseproject.cinedev.presentation.services;

import com.databaseproject.cinedev.application.services.user.UserService;
import com.databaseproject.cinedev.application.utils.Utils;
import com.databaseproject.cinedev.domain.models.base.User;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String email, String password) {
        User userFromDb = userService.getUserByEmail(email);
        if (userFromDb == null) {
            Utils.sendMessage("User not found. Please check your information or register one account", Alert.AlertType.ERROR);
            return null;
        }

        User existingUser = userService.getUserWithRolesById(userFromDb.getId());
        if (existingUser.isPasswordCheck(password, existingUser.getPassword())) {
            return existingUser;
        }

        return null;
    }

    public boolean register(User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return false;
        }

        userService.saveUser(user);
        return true;
    }
}
