package com.nguyenklinh.shopapp.repositorys;

import com.nguyenklinh.shopapp.models.Order;
import com.nguyenklinh.shopapp.utils.MessageKeys;
import com.nguyenklinh.shopapp.utils.QueryConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    @Query(value = QueryConstants.QUERY_ORDERS_VALUE,
            countQuery = QueryConstants.QUERY_ORDERS_COUNT , nativeQuery = true)
    Page<Order> searchOrders(@Param("keyword") String keyword, Pageable pageable);
}
