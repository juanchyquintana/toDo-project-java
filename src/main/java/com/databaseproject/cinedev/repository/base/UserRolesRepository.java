package com.databaseproject.cinedev.repository.base;

import com.databaseproject.cinedev.models.base.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
    @Query("SELECT COUNT(ur) > 0 FROM UserRoles ur WHERE ur.user.id = :userId AND ur.roles.id = :adminRoleId")
    boolean isUserAdmin(@Param("userId") Integer userId, @Param("adminRoleId") Integer adminRoleId);

}
