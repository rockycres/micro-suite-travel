package com.learn.customersvc.common.enumeration;

/**
 * Enum that classifies the customer profile 's type.
 *
 */
public enum CustomerProfileEnum {

    BASIC("BASIC"), PREMIUM("PREMIUM");

    private String value;

    private CustomerProfileEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
