package com.insignis.batch.config.job.loadproductjob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.insignis.batch.model.loadproductjob.Category;
import com.insignis.batch.model.loadproductjob.Product;
import com.insignis.batch.model.loadproductjob.Supplier;
import com.insignis.batch.model.loadproductjob.TransactionDataDTO;
import com.insignis.batch.repository.loadproductjob.CategoryRepository;
import com.insignis.batch.repository.loadproductjob.SupplierRepository;

public class LoadProductItemProcessor implements ItemProcessor<TransactionDataDTO, Product> {

	private Set<String> uniqueProductItem = new HashSet<String>();
	private CategoryRepository categoryRepository;
	private List<Category> categories = new ArrayList<Category>();

	private SupplierRepository supplierRepository;
	private List<Supplier> suppliers = new ArrayList<>();

	@Autowired
	public LoadProductItemProcessor(CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
		this.categoryRepository = categoryRepository;
		this.supplierRepository = supplierRepository;
	}

	@PostConstruct
	public void init() {
		categories = this.categoryRepository.findAll();
		suppliers = this.supplierRepository.findAll();
	}

	@Override
	public Product process(TransactionDataDTO item) throws Exception {
		if (!uniqueProductItem.contains(item.getStockCode())) {
			Product product = new Product();
			product.setName(item.getDescription());
			product.setProductId(item.getStockCode());
			product.setCategories(getRandomCategory());
			product.setSupplierId(getRandomSupplier().getId());
			product.setUnitPrice(item.getUnitPrice());
			uniqueProductItem.add(item.getStockCode());
			return product;
		}
		return null;
	}

	public List<Category> getRandomCategory() {
		List<Category> randomCat = Arrays.asList(categories.get(new Random().nextInt(categories.size())));
		return randomCat;
	}

	public Supplier getRandomSupplier() {
		return suppliers.get(new Random().nextInt(suppliers.size()));
	}

}
