package com.myRetail.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents an entry in the user collection of the database
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "user")
public class User {
    private String username;
    private String password;
}
