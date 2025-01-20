package com.nguyenklinh.shopapp.utils;

public class MessageKeys {
    //success
    public static final String SUCCESS_CREATED_ORDER = "success.order.created";
    public static final String SUCCESS_DELETED_ORDER = "success.order.deleted";
    public static final String SUCCESS_DELETED_ORDER_DETAIL = "success.order_detail.deleted";
    public static final String SUCCESS_DELETED_PRODUCT = "success.product.deleted";

    // System Errors
    public static final String UNCATEGORIZED_EXCEPTION = "error.uncategorized";
    public static final String UNAUTHENTICATED = "error.unauthenticated";
    public static final String ACCESS_DENIED = "error.access.denied";
    public static final String UNSUPPORTED_MEDIA_TYPE = "error.unsupported.media.type";

    // Authentication & User Related
    public static final String USER_EXISTED = "error.user.existed";
    public static final String USER_NOT_EXISTED = "error.user.not.existed";
    public static final String USER_NOT_FOUND = "error.user.not.found";
    public static final String USER_ID_INVALID = "error.user.id.invalid";
    public static final String USERNAME_NOT_FOUND = "error.username.not.found";
    public static final String LOGIN_INVALID = "error.login.invalid";

    // Password Related
    public static final String PASSWORD_NOT_MATCH = "error.password.not.match";
    public static final String PASSWORD_BLANK = "error.password.blank";

    // Product Related
    public static final String PRODUCT_CREATE_FAILED = "error.product.create.failed";
    public static final String PRODUCT_NAME_BLANK = "error.product.name.blank";
    public static final String PRODUCT_NOT_FOUND = "error.product.not.found";
    public static final String PRODUCT_ID_INVALID = "error.product.id.invalid";
    public static final String PRODUCT_QUANTITY_INVALID = "error.product.quantity.invalid";
    public static final String PRODUCT_MAX_IMAGES = "error.product.max.images";

    // Category Related
    public static final String CATEGORY_NOT_FOUND = "error.category.not.found";
    public static final String CATEGORY_NAME_BLANK = "error.category.name.blank";

    // File Related
    public static final String FILE_NOT_FOUND = "error.file.not.found";
    public static final String INVALID_FILE_NAME = "error.file.name.invalid";
    public static final String IMAGE_MAX_LIMIT = "error.image.max.limit";
    public static final String FILE_TYPE_INVALID = "error.file.type.invalid";
    public static final String FILE_SAVE = "error.file.save";
    public static final String FILE_SIZE_LARGE = "error.file.size.large";
    public static final String UPLOAD_DIR_CREATE = "error.upload.dir.create";

    // Order Related
    public static final String ORDER_NOT_FOUND = "error.order.not.found";
    public static final String ORDER_PROCESSED = "error.order.processed";
    public static final String ORDER_DETAIL_NOT_FOUND = "error.order.detail.not.found";
    public static final String ORDER_DETAILS_EMPTY = "error.order.details.empty";

    // Validation Related
    public static final String INVALID_DOB = "error.invalid.dob";
    public static final String PRICE_INVALID = "error.price.invalid";
    public static final String PHONE_BLANK = "error.phone.blank";
    public static final String PHONE_SIZE = "error.phone.size";
    public static final String ADDRESS_BLANK = "error.address.blank";
    public static final String EMAIL_INVALID = "error.email.invalid";
    public static final String SHIPPING_DATE_INVALID = "error.shipping.date.invalid";
    public static final String TOTAL_MONEY_INVALID = "error.total.money.invalid";

    // Security Related
    public static final String AUTH_MANAGER = "error.auth.manager";
    public static final String SECURITY_FILTER_CHAIN = "error.security.filter.chain";
    public static final String ROLE_NOT_FOUND = "error.role.not.found";

    private MessageKeys() {
    }
}
