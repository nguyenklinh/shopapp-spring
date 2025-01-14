package com.nguyenklinh.shopapp.services.impl;

import com.nguyenklinh.shopapp.dtos.ProductImageDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.models.Product;
import com.nguyenklinh.shopapp.models.ProductImage;
import com.nguyenklinh.shopapp.repositorys.ProductImageRepository;
import com.nguyenklinh.shopapp.repositorys.ProductRepository;
import com.nguyenklinh.shopapp.services.ProductImageService;
import com.nguyenklinh.shopapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    @Value("${app.max.images.per-product}")
    private int maxImagesPerProduct;
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    @Override
    public List<ProductImage> uploadProductImages(Long productId, List<MultipartFile> files) throws IOException {
        return null;
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO)  {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new MyException(ErrorCode.CAN_NOT_FIND_PRODUCT));

        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= maxImagesPerProduct) {
            throw new MyException(ErrorCode.MAX_IMAGE_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
}
