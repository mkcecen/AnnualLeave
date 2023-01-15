package com.annualLeave.enums;

public enum Department {
    DEVELOPER("DEVELOPER"),
    NONE("NONE"),
    HR("HR"),
    DESIGNER("DESIGNER");

    private final String value;
    private Department(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
