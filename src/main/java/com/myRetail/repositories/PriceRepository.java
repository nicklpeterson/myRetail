package com.myRetail.repositories;

import com.myRetail.models.Price;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface PriceRepository extends MongoRepository<Price, String> {
    Optional<Price> findByProductId(String productId);
}
