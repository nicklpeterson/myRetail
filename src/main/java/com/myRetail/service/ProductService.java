package com.myRetail.service;

import com.myRetail.models.PriceModel;
import com.myRetail.exceptions.ProductNotFoundException;
import com.myRetail.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public PriceModel saveProduct(PriceModel productDto) {
        return productRepository.save(productDto);
    }

    public PriceModel selectProduct(String id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }
}
