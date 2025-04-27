package com.databaseproject.cinedev.services.base.userRole;

import com.databaseproject.cinedev.models.base.Roles;
import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.models.base.UserRoles;
import com.databaseproject.cinedev.models.base.compositeKey.UserRoleId;
import com.databaseproject.cinedev.repository.base.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService implements IUserRoleService{
    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public void assignRoleToUser(User user, Roles role) {
        UserRoleId id = new UserRoleId(user.getId(), role.getId());
        UserRoles userRoles = new UserRoles(id, user, role);

        userRolesRepository.save(userRoles);
    }

    @Override
    public boolean isAdmin(Integer userId) {
        final int ADMIN_ROLE_ID = 1;
        return userRolesRepository.isUserAdmin(userId, ADMIN_ROLE_ID);
    }

}
