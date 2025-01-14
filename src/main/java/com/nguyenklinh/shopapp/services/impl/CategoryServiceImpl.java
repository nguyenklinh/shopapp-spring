package com.nguyenklinh.shopapp.services.impl;

import com.nguyenklinh.shopapp.dtos.CategoryDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.models.Category;
import com.nguyenklinh.shopapp.repositorys.CategoryRepository;
import com.nguyenklinh.shopapp.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new MyException(ErrorCode.CATEGORY_ID_NOT_FOUND));
        category.setName(categoryDTO.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long id) {
        if (!categoryRepository.existsById(id)){throw new MyException(ErrorCode.CATEGORY_ID_NOT_FOUND);}

        categoryRepository.deleteById(id);
    }
}
