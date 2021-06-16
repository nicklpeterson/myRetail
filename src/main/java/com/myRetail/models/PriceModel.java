package com.myRetail.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@Getter
@Setter
public class PriceModel {

    @Id
    private String id;

    private double price;

    private String currency;
}
