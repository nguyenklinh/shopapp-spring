package com.nguyenklinh.shopapp.services.impl;

import com.nguyenklinh.shopapp.dtos.ProductImageDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.InvalidParamException;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.models.Product;
import com.nguyenklinh.shopapp.models.ProductImage;
import com.nguyenklinh.shopapp.repositorys.ProductImageRepository;
import com.nguyenklinh.shopapp.repositorys.ProductRepository;
import com.nguyenklinh.shopapp.services.ProductImageService;
import com.nguyenklinh.shopapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductService productService;
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    @Override
    public List<ProductImage> uploadProductImages(Long productId, List<MultipartFile> files) throws IOException {
        return null;
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new MyException(ErrorCode.CAN_NOT_FIND_PRODUCT));

        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= 5) {
            throw new InvalidParamException(
                    "Number of images must be <= " +5);
        }
        return productImageRepository.save(newProductImage);
    }
}
