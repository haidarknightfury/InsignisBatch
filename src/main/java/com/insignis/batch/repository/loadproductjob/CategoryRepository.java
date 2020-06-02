package com.insignis.batch.repository.loadproductjob;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.insignis.batch.model.loadproductjob.Category;
import com.insignis.batch.model.loadproductjob.CategoryTypeEnum;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

	public Category findByCategory(CategoryTypeEnum category);
}
