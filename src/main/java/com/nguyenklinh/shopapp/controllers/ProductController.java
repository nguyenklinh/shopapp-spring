package com.nguyenklinh.shopapp.controllers;

import com.github.javafaker.Faker;
import com.nguyenklinh.shopapp.dtos.ProductDTO;
import com.nguyenklinh.shopapp.dtos.ProductImageDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.models.Product;
import com.nguyenklinh.shopapp.models.ProductImage;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.responses.ProductListResponse;
import com.nguyenklinh.shopapp.responses.ProductResponse;
import com.nguyenklinh.shopapp.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping("${api.prefix}/products")
//http://localhost:8083/v1/api/products
@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO)  {
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(ApiResponse.builder()
                    .success(true)
                    .result(newProduct)
                    .build());

    }

    @GetMapping("")
    public ResponseEntity<?> getProducts(
            @RequestParam("page")     int page,
            @RequestParam("limit")    int limit
    ) {
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("createdAt").descending());

        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        ProductListResponse response = ProductListResponse.builder()
                .totalPages(productPage.getTotalPages())
                .currentPage(page)
                .totalElements(productPage.getTotalElements())
                .products(productPage.getContent())
                .build();

        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(response)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
        Product existingProduct = productService.getProductById(productId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(ProductResponse.fromProduct(existingProduct))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result("product has been deleted")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") Long productId,
            @Valid @RequestBody ProductDTO productDTO
    ) {
        Product updatedProduct = productService.updateProduct(productId, productDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(updatedProduct)
                .build());
    }

}
