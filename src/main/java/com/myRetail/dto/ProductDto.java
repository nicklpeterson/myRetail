package com.myRetail.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public ProductDto(String id, String name) {
        this.id = id;
        this.name = name;
        this.currentPrice = null;
    }

    @AllArgsConstructor
    @Getter
    public static class CurrentPrice {
        private final String currencyCode;
        private final double price;
    }
}
