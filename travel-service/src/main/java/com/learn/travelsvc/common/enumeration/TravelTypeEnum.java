package com.learn.travelsvc.common.enumeration;

/**
 * Enum that classifies the travel's type.
 *
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
public enum TravelTypeEnum {

    ONE_WAY("ONE-WAY"), RETURN("RETURN");

    private String value;

    private TravelTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
