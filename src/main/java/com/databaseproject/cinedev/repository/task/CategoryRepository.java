package com.databaseproject.cinedev.repository.task;

import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.models.task.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByNameIgnoreCase(String name);

    List<Category> findByUserAdminId(User user);

    @Query("SELECT COUNT(t) > 0 FROM Task t WHERE t.category.id = :categoryId")
    boolean existsTasksByCategoryId(@Param("categoryId") Integer categoryId);
}
