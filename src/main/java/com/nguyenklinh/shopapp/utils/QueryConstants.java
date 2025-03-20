package com.nguyenklinh.shopapp.utils;

public class QueryConstants {
    // query
    //query orders
    public static final String WHERE_ACTIVE_AND_KEYWORD = "WHERE o.is_active = 1 "
            + "AND (:keyword IS NULL OR (o.fullname LIKE CONCAT('%', :keyword, '%') OR o.address LIKE CONCAT('%', :keyword, '%')))";;
    public static final String QUERY_ORDERS_VALUE = "SELECT * FROM orders o " + WHERE_ACTIVE_AND_KEYWORD;
    public static final String QUERY_ORDERS_COUNT = "SELECT count(*) FROM orders o " + WHERE_ACTIVE_AND_KEYWORD;
}
