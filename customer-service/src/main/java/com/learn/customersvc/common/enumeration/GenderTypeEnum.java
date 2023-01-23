package com.learn.customersvc.common.enumeration;

/**
 * Enum that classifies the customer's type.
 *
 */
public enum GenderTypeEnum {

    MALE("MALE"), FEMALE("FEMALE"), TRANSGENDER("TRANSGENDER") ;

    private String value;

    private GenderTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
