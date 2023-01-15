package com.annualLeave.enums;

public enum GenderType {
    NONE("NONE"),
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String value;
    private GenderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
