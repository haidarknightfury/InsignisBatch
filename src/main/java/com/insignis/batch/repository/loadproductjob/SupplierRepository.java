package com.insignis.batch.repository.loadproductjob;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.insignis.batch.model.loadproductjob.Supplier;

@Repository
public interface SupplierRepository extends MongoRepository<Supplier, String> {

	Optional<Supplier> findByNameIgnoreCase(String name);
}
