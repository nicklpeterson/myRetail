package com.myRetail.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "price")
public class PriceModel {

    @Id
    private String id;

    @Field("price")
    private double price;

    @Field("currency")
    private String currency;
}
