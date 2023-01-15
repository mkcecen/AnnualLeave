package com.annualLeave.enums;

public enum StatusType {
    ACTIVE("ACTIVE"),
    LEFT("LEFT");

    private final String value;
    private StatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
