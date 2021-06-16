package com.myRetail.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductDto {
    private final String id;
    private final String name;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final CurrentPrice currentPrice;

    public ProductDto(String id, String name, String currencyCode, double price) {
        this.id = id;
        this.name = name;
        this.currentPrice = new CurrentPrice(currencyCode, price);
    }

    public String getCurrencyCode() {
        return this.currentPrice.getCurrencyCode();
    }

    public double getPrice() {
        return this.currentPrice.getPrice();
    }

    @AllArgsConstructor
    @Getter
    private static class CurrentPrice {
        private final String currencyCode;
        private final double price;
    }
}
