package com.annualLeave.enums;

public enum PersonType {
    ADMIN("ADMIN"),
    OTHER("OTHER");
    private final String value;
    private PersonType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
