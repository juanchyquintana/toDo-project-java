package com.databaseproject.cinedev.services.tasks.category;

import com.databaseproject.cinedev.models.task.Category;

import java.util.List;

public interface ICategoryService {
    public void addCustomCategory(Category category);

    public void removeCustomCategory(Category category);

    public List<Category> getAllCategories();

    public Category getByName(String name);

    public void categoriesFromUserAdmin(Integer userId);

    public boolean hasTasksAssociated(Category category);

}
