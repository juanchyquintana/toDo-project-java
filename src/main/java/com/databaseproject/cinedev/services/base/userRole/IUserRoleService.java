package com.databaseproject.cinedev.services.base.userRole;

import com.databaseproject.cinedev.models.base.Roles;
import com.databaseproject.cinedev.models.base.User;

public interface IUserRoleService {
    void assignRoleToUser(User user, Roles role);

    boolean isAdmin(Integer userId);
}
