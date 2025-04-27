package com.databaseproject.cinedev.repository.base;

import com.databaseproject.cinedev.models.base.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Roles findByName(String name);
}
