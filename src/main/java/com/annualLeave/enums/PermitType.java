package com.annualLeave.enums;

public enum PermitType {
    YEARLY("YEARLY"),
    EXCUSE("EXCUSE");

    private final String value;
    private PermitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
