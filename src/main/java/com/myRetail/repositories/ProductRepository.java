package com.myRetail.repositories;

import com.myRetail.models.PriceModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProductRepository extends MongoRepository<PriceModel, String> {
}
