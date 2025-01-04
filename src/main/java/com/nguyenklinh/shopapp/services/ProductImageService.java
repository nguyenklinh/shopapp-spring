package com.nguyenklinh.shopapp.services;

import com.nguyenklinh.shopapp.dtos.ProductImageDTO;
import com.nguyenklinh.shopapp.models.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {
    public List<ProductImage> uploadProductImages(Long productId, List<MultipartFile> files) throws IOException;
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
}
