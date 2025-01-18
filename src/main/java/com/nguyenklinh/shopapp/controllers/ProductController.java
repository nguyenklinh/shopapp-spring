package com.nguyenklinh.shopapp.controllers;

import com.nguyenklinh.shopapp.components.MessageUtil;
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
import com.nguyenklinh.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("${api.prefix}/products")
//http://localhost:8083/v1/api/products
@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;
    private final MessageUtil messageUtil;
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
                .message(messageUtil.getMessage(MessageKeys.SUCCESS_DELETED_PRODUCT))
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
