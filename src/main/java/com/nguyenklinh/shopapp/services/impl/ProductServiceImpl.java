package com.nguyenklinh.shopapp.services.impl;

import com.nguyenklinh.shopapp.dtos.ProductDTO;
import com.nguyenklinh.shopapp.dtos.ProductImageDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.models.Category;
import com.nguyenklinh.shopapp.models.Product;
import com.nguyenklinh.shopapp.models.ProductImage;
import com.nguyenklinh.shopapp.repositorys.CategoryRepository;
import com.nguyenklinh.shopapp.repositorys.ProductImageRepository;
import com.nguyenklinh.shopapp.repositorys.ProductRepository;
import com.nguyenklinh.shopapp.responses.ProductResponse;
import com.nguyenklinh.shopapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Category existingCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new MyException(ErrorCode.CATEGORY_ID_NOT_FOUND));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long productId)  {
        return productRepository.findById(productId).
                orElseThrow(()-> new MyException(ErrorCode.CAN_NOT_FIND_PRODUCT));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository
                .findAll(pageRequest)
                .map(product -> ProductResponse.fromProduct(product));
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO)  {
        Product existingProduct = getProductById(id);
        //copy các thuộc tính từ DTO -> Product
        //Có thể sử dụng ModelMapper
        Category existingCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() -> new MyException(ErrorCode.CATEGORY_ID_NOT_FOUND));

        existingProduct.setName(productDTO.getName());
        existingProduct.setCategory(existingCategory);
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setThumbnail(productDTO.getThumbnail());

        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product  = productRepository.findById(id)
                .orElseThrow(()->
                        new MyException(ErrorCode.CAN_NOT_FIND_PRODUCT,id,5L));
        productRepository.delete(product);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
