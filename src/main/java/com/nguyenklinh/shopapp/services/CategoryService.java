package com.nguyenklinh.shopapp.services;

import com.nguyenklinh.shopapp.dtos.CategoryDTO;
import com.nguyenklinh.shopapp.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO category);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);
    void deleteCategory(long id);
}
