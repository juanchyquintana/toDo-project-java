package com.databaseproject.cinedev.repository.base;

import com.databaseproject.cinedev.models.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.userRoles ur " +
            "LEFT JOIN FETCH ur.roles " +
            "WHERE u.id = :id")
    User findByIdWithRoles(@Param("id") Integer id);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.roles")
    List<User> findAllWithRoles();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks t LEFT JOIN FETCH t.category c LEFT JOIN FETCH c.userAdminId WHERE u.id = :id")
    User findByIdWithEverything(@Param("id") Integer id);
}
