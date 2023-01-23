package com.learn.ordersvc.common.enumeration;


/**
 * Enum that classifies the OrderStatus type.
 */
public enum OrderStatusEnum {

    PENDING("PENDING"), FAILED("FAILED"), COMPLETED("COMPLETED");

    private String value;

    private OrderStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
