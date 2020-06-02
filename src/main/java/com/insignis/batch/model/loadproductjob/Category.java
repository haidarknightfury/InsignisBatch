package com.insignis.batch.model.loadproductjob;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Category {

	@Id
	private String id;
	@Indexed
	private CategoryTypeEnum category;

	public Category() {
	}

	public Category(CategoryTypeEnum category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CategoryTypeEnum getCategory() {
		return category;
	}

	public void setCategory(CategoryTypeEnum category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", category=" + category + "]";
	}

}
