package com.happyaging.fallprevention.product.exception;

import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;

public class ProductNotFoundException extends UnauthorizedException {
    private static final String errorMsg = "PRODUCT_NOT_FOUND";
    public ProductNotFoundException() {super(errorMsg);}
}
