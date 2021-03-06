package com.myRetail.controller;

import com.myRetail.dto.ProductDto;
import com.myRetail.exceptions.BadPriceUpdateRequest;
import com.myRetail.exceptions.ProductNotFoundException;
import com.myRetail.exceptions.ProductServiceException;
import com.myRetail.models.Price;
import com.myRetail.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;

/**
 * Controller responsible for the two /product/** REST endpoints
 */

@RestController
@Slf4j
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/products/{id}", produces = "application/json")
    public ProductDto getProductById(@PathVariable String id) throws ProductNotFoundException {
        final ProductDto productDto;
        try {
            productDto = productService.selectProduct(id);
        } catch (ProductServiceException e) {
            log.error(e.getMessage());
            throw new InternalError();
        }
        return productDto;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/products/price/{id}")
    public ProductDto updateProductPrice(@PathVariable String id, @RequestBody Price price) throws ProductNotFoundException, BadPriceUpdateRequest {
        if (price.getPrice() == null || price.getCurrency() == null) {
            throw new BadPriceUpdateRequest();
        }
        try {
            Currency.getInstance(price.getCurrency());
        } catch (IllegalArgumentException e) {
            throw new BadPriceUpdateRequest();
        }
        final ProductDto productDto;
        try {
            productDto = productService.updatePrice(id, price);
        } catch (ProductServiceException e) {
            log.error(e.getMessage());
            throw new InternalError();
        }
        return productDto;
    }

}
