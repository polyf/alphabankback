package com.alpha.bank.model.enums;

public enum StatusEnum {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String label;

    StatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}