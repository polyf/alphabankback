package com.alpha.bank.model.enums;

public enum CompanySizeEnum {
    MICRO("Micro"),
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large");

    private final String label;

    CompanySizeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}