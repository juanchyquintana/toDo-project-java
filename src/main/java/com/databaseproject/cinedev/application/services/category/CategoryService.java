package com.databaseproject.cinedev.application.services.category;

import com.databaseproject.cinedev.domain.models.base.User;
import com.databaseproject.cinedev.domain.models.task.Category;
import com.databaseproject.cinedev.domain.repository.task.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void addCustomCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void removeCustomCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public void categoriesFromUserAdmin(Integer userId) {
        var dummyUser = new User();
        dummyUser.setId(userId); // Hibernate usará solo el ID para el filtro

        List<Category> categories = categoryRepository.findByUserAdminId(dummyUser);
        for (Category category : categories) {
            category.setUserAdminId(null);
            categoryRepository.save(category);
        }
    }

    @Override
    public boolean hasTasksAssociated(Category category) {
        return categoryRepository.existsTasksByCategoryId(category.getId());
    }
}
