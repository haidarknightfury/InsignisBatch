package com.insignis.batch.repository.loadproductjob;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.insignis.batch.model.loadproductjob.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	public Optional<Product> findByNameIgnoreCase(String name);

	public Product findByProductId(String productId);

}
