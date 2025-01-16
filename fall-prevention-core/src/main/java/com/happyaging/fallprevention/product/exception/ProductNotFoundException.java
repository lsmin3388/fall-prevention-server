package com.happyaging.fallprevention.product.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    private static final String errorMsg = "PRODUCT_NOT_FOUND";
    public ProductNotFoundException() {super(errorMsg);}
}
