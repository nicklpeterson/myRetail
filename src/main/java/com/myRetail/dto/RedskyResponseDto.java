package com.myRetail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Represents a response from the Redsky api
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RedskyResponseDto {
    private String id;
    private String title;

    @JsonProperty("product")
    private void unpackNested(Map<String, Map<String, Object>> product) {
        try {
            if (product.containsKey("available_to_promise_network")
                    && product.get("available_to_promise_network").containsKey("product_id")) {
                this.id = (String) product.get("available_to_promise_network").get("product_id");
            } else {
                this.id = null;
            }

            // TODO: The cast to Map<String, Object> here is messy. Improve it.
            if (product.containsKey("item")
                    && product.get("item").containsKey("product_description")
                    && ((Map<String, Object>) product.get("item").get("product_description")).containsKey("title")) {
                this.title = (String) ((Map<String, Object>) product.get("item").get("product_description")).get("title");
            } else {
                this.title = null;
            }
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            this.id = null;
            this.title = null;
        }
    }

}
