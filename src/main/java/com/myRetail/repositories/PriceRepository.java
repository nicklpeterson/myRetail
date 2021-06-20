package com.myRetail.repositories;

import com.myRetail.models.Price;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for accessing the price collection in the Database
 */
@Component
@Repository
public interface PriceRepository extends MongoRepository<Price, String> {
    Optional<Price> findByProductId(String productId);
}
