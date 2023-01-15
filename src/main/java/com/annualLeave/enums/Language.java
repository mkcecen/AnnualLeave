package com.annualLeave.enums;

public enum Language {
    TR("TR"),
    EN("EN");

    private final String value;
    private Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
