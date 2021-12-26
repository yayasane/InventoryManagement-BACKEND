package com.yayaveli.inventorymanagement.exceptions;

public enum ErrorCodes {
    ITEM_NOT_FOUND(1000),
    ITEM_NOT_VALID(1001),
    CATEGORY_NOT_FOUND(2000),
    // TODO complete the rest of the error code
    CLIENT_NOT_FOUND(3000),
    CLIEN_ORDER_NOT_FOUND(4000),
    COMPANY_NOT_FOUND(5000),
    PROVIDER_ORDER_NOT_FOUND(6000),
    PROVIDER_NOT_FOUND(7000),
    CLIENT_ORDER_LINE_NOT_FOUND(8000),
    PROVIDER_ORDER_LINE_NOT_FOUND(9000),
    SALE_LINE_NOT_FOUND(10000),
    INVENTORY_MOVEMENT_NOT_FOUND(11000),
    USER_NOT_FOUND(12000),
    SALE_NOT_FOUND(13000);

    private int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
