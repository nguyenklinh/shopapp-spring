package com.nguyenklinh.shopapp.repositorys;

import com.nguyenklinh.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    @Query("SELECT p FROM Product p WHERE " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR :keyword IS NULL OR :keyword = '')" +
            "AND (:categoryId IS NULL OR (p.category IS NOT NULL AND p.category.id = :categoryId))" +
            "AND (:minPrice IS NULL OR p.price >= :minPrice)" +
            "AND (:maxPrice IS null OR p.price <= :maxPrice)"
    )
    Page<Product> searchProducts(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}
