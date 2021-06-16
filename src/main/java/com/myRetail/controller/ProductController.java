package com.myRetail.controller;

import com.myRetail.clients.RedskyTargetClient;
import com.myRetail.dto.ProductDto;
import com.myRetail.dto.RedskyResponseDto;
import com.myRetail.exceptions.HttpRequestException;
import com.myRetail.exceptions.ProductNotFoundException;
import com.myRetail.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final RedskyTargetClient redskyTargetClient;

    @GetMapping(path = "/products/{id}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable String id) throws ProductNotFoundException {
        final Map<String, Object> response = new HashMap<>();
        try {
            final RedskyResponseDto redskyResponseDto = redskyTargetClient.requestProduct(id);
            if (redskyResponseDto.getId() == null || redskyResponseDto.getTitle() == null) {
                throw new ProductNotFoundException();
            }

            final ProductDto productDto = new ProductDto(redskyResponseDto.getId(), redskyResponseDto.getTitle(), "CAD", 0);
            response.put("product", productDto);
            response.put("success", true);

        } catch (HttpRequestException e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }
}
