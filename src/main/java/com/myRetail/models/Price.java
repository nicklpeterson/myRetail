package com.myRetail.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "price")
public class Price {

    private String productId;
    private double price;
    private String currency;
}
