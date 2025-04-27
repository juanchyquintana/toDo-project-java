package com.databaseproject.cinedev.services.base.user;

import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.repository.base.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUserWithRolesById(Integer id) {
        return userRepository.findByIdWithRoles(id);
    }

    public List<User> getAllUsersWithRoles() {
        return userRepository.findAllWithRoles();
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByIdWithEverything(Integer id) {
        return userRepository.findByIdWithEverything(id);
    }
}
