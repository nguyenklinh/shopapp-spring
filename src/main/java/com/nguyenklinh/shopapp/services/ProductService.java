package com.nguyenklinh.shopapp.services;

import com.nguyenklinh.shopapp.dtos.ProductDTO;
import com.nguyenklinh.shopapp.dtos.ProductImageDTO;
import com.nguyenklinh.shopapp.models.Product;
import com.nguyenklinh.shopapp.models.ProductImage;
import com.nguyenklinh.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    Product createProduct(ProductDTO productDTO) ;

    Product getProductById(Long productId) ;

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    Product updateProduct(Long id, ProductDTO productDTO) ;

    void deleteProduct(Long id);

    boolean existsByName(String name);

}
