package com.myRetail.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents an entry in the price collection of the database
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "price")
public class Price {
    @Id
    private String productId;
    private Double price;
    private String currency;
}
