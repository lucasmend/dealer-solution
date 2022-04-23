package com.exercise.dealersolution.business;

public enum ProductStatus {
    INVALID(-1), OUTDATED(0), IN_TRANSPORT(1), AVAILABLE(2);

    private final int value;

    ProductStatus(int enumValue) {
        value = enumValue;
    }

    public int getValue() {
        return value;
    }
}
