package com.databaseproject.cinedev.services.base.roles;

import com.databaseproject.cinedev.models.base.Roles;
import com.databaseproject.cinedev.repository.base.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    RolesRepository rolesRepository;

    @Override
    public Roles findByName(String role) {
        return rolesRepository.findByName(role);
    }

    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Roles findById(Integer id) {
        return rolesRepository.findById(id).orElse(null);
    }
}
