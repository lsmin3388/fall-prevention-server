package com.happyaging.fallprevention.product.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class ProductDuplicatedException extends NotFoundException {
    private static final String errorMsg = "PRODUCT_DUPLICATED";
    public ProductDuplicatedException() {super(errorMsg);}
}
