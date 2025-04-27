package com.databaseproject.cinedev.services.base.roles;

import com.databaseproject.cinedev.models.base.Roles;

import java.util.List;

public interface IRoleService {
    public Roles findByName(String role);

    public List<Roles> getAllRoles();

    Roles findById(Integer id);
}
