package com.dimo_test.ecommerce.domain;

public enum Labels {
    DRINK,
    FOOD,
    CLOTHES,
    LIMITED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
