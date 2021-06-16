package com.myRetail.service;

import com.myRetail.clients.RedskyTargetClient;
import com.myRetail.dto.ProductDto;
import com.myRetail.dto.RedskyResponseDto;
import com.myRetail.exceptions.HttpRequestException;
import com.myRetail.exceptions.ProductServiceException;
import com.myRetail.models.Price;
import com.myRetail.exceptions.ProductNotFoundException;
import com.myRetail.repositories.PriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final PriceRepository priceRepository;
    private final RedskyTargetClient redskyTargetClient;

    public void saveProduct(Price productDto) {
        priceRepository.save(productDto);
    }

    public ProductDto selectProduct(String id) throws ProductNotFoundException, ProductServiceException {
        final ProductDto productDto;
        try {
            Optional<Price> priceOptional = priceRepository.findByProductId(id);
            final RedskyResponseDto redskyResponseDto = redskyTargetClient.requestProduct(id);
            if (redskyResponseDto.getTitle() == null || redskyResponseDto.getId() == null || !priceOptional.isPresent()) {
                throw new ProductNotFoundException();
            }
            final Price price = priceOptional.get();
            productDto = new ProductDto(id, redskyResponseDto.getTitle(), price.getCurrency(), price.getPrice());
        } catch (HttpRequestException e) {
            throw new ProductServiceException(e.getMessage(), e);
        }
        return productDto;
    }
}
