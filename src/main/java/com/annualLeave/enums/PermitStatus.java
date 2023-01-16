package com.annualLeave.enums;

public enum PermitStatus {
    WAITING("WAITING"),
    DENIED("DENIED"),
    APPROVED("APPROVED");

    private final String value;
    private PermitStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
