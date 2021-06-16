package com.myRetail.controller;

import com.myRetail.dto.ProductDto;
import com.myRetail.exceptions.ProductNotFoundException;
import com.myRetail.exceptions.ProductServiceException;
import com.myRetail.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(path = "/products/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable String id) throws ProductNotFoundException {
        final Map<String, Object> response = new HashMap<>();
        try {
            final ProductDto productDto = productService.selectProduct(id);
            response.put("product", productDto);
            response.put("success", true);
        } catch (ProductServiceException e) {
            log.error(e.getMessage());
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }
}
