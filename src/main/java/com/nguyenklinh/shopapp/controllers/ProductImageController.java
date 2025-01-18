package com.nguyenklinh.shopapp.controllers;

import com.nguyenklinh.shopapp.components.MessageUtil;
import com.nguyenklinh.shopapp.dtos.ProductImageDTO;
import com.nguyenklinh.shopapp.enums.ErrorCode;
import com.nguyenklinh.shopapp.exceptions.MyException;
import com.nguyenklinh.shopapp.models.Product;
import com.nguyenklinh.shopapp.models.ProductImage;
import com.nguyenklinh.shopapp.responses.ApiResponse;
import com.nguyenklinh.shopapp.services.ProductImageService;
import com.nguyenklinh.shopapp.services.ProductService;
import com.nguyenklinh.shopapp.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/product-images")
// http://localhost:8083/api/v1/product-images
public class ProductImageController {
    @Value("${app.max.image.size}")
    private long maxImageSize;

    @Value("${app.max.images.per-product}")
    private int maxImagesPerProduct;

    private final ProductImageService productImageService;
    private final ProductService productService;
    private final MessageUtil messageUtil;
    @PostMapping(value = "uploads/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("productId") Long productId,
            @RequestParam("files") List<MultipartFile> files
    ){
        Product existingProduct = productService.getProductById(productId);
        files = files == null ? new ArrayList<>() : files;
        if(files.size() > maxImagesPerProduct) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.builder()
                            .success(false)
                            .message(messageUtil.getMessage(MessageKeys.IMAGE_MAX_LIMIT))
                            .build());
        }
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if(file.getSize() == 0) {
                continue;
            }
            // Kiểm tra kích thước file và định dạng
            if(file.getSize() > maxImageSize) {
                throw new MyException(ErrorCode.FILE_SIZE_TOO_LARGE);
            }
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")) {
                throw new MyException(ErrorCode.INVALID_FILE_TYPE);
            }

            // Lưu file và cập nhật thumbnail trong DTO
            String filename = storeFile(file);

            //lưu vào đối tượng product trong DB
            ProductImage productImage = productImageService.createProductImage(
                    existingProduct.getId(),
                    ProductImageDTO.builder().imageUrl(filename).build()
            );
            productImages.add(productImage);
        }
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .result(productImages)
                .build());
    }
    private String storeFile(MultipartFile file) {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new MyException(ErrorCode.INVALID_FILE_TYPE);
        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            try {
                Files.createDirectories(uploadDir);
            } catch (IOException e) {
                throw new MyException(ErrorCode.CAN_NOT_CREATE_UPLOAD_DIR);
            }
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        // Sao chép file vào thư mục đích
        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new MyException(ErrorCode.CAN_NOT_SAVE_FILE);
        }
        return uniqueFilename;
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
