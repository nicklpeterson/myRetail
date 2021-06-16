package com.myRetail.dto;

import lombok.*;

@Getter
public class ProductDto {
    private final String id;
    private final String name;
    @Setter(AccessLevel.NONE)
    private final CurrentPrice currentPrice;

    public ProductDto(String id, String name, String currencyCode, double price) {
        this.id = id;
        this.name = name;
        this.currentPrice = new CurrentPrice(currencyCode, price);
    }

    @AllArgsConstructor
    @Getter
    private static class CurrentPrice {
        private final String currencyCode;
        private final double price;
    }
}
