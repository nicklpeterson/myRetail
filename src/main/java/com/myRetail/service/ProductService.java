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

    public void savePrice(Price productDto) {
        priceRepository.save(productDto);
    }

    public ProductDto selectProduct(String id) throws ProductNotFoundException, ProductServiceException {
        final ProductDto productDto;
        try {
            final Optional<Price> priceOptional = priceRepository.findByProductId(id);
            final RedskyResponseDto redskyResponseDto = redskyTargetClient.requestProduct(id);
            if (redskyResponseDto.getTitle() == null || redskyResponseDto.getId() == null) {
                throw new ProductNotFoundException();
            }

            // If the price is not in our database, but we have product information from the redsky API
            // we will not include price data in the response
            if (!priceOptional.isPresent()) {
                productDto = new ProductDto(id, redskyResponseDto.getTitle());
            } else {
                final Price price = priceOptional.get();
                productDto = new ProductDto(id, redskyResponseDto.getTitle(), price.getCurrency(), price.getPrice());
            }

        } catch (HttpRequestException e) {
            throw new ProductServiceException(e.getMessage(), e);
        }
        return productDto;
    }

    public ProductDto updatePrice(String id, Price price) throws ProductNotFoundException, ProductServiceException {
        final ProductDto productDto = selectProduct(id);
        final Price newPrice = Price.builder()
                .price(price.getPrice())
                .currency(price.getCurrency())
                .productId(id)
                .build();
        priceRepository.save(newPrice);
        return new ProductDto(id, productDto.getName(), newPrice.getCurrency(), newPrice.getPrice());
    }
}
