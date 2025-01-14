package com.nguyenklinh.shopapp.controllers;

import com.nguyenklinh.shopapp.dtos.CategoryDTO;
import com.nguyenklinh.shopapp.models.Category;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping()
    public ResponseEntity<?> creatCategory(@Valid CategoryDTO categoryDTO){
        Category category = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(category)
                .build());
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategory(){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(categories)
                .build());
    }

   @PutMapping("/{id}")
   public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,CategoryDTO categoryDTO){
        Category category = categoryService.updateCategory(id,categoryDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(category)
                .build());
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("category has been deleted")
                .build());
   }

}
