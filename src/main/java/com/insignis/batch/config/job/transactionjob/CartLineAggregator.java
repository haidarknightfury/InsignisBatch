package com.insignis.batch.config.job.transactionjob;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.file.transform.LineAggregator;

import com.insignis.batch.model.transactionjob.Cart;
import com.insignis.batch.model.transactionjob.Product;

public class CartLineAggregator implements LineAggregator<Cart> {

	@Override
	public String aggregate(Cart item) {
		List<Product> products = item.getProducts();
		String cartProducts = products.stream().map(Product::getName).distinct().collect(Collectors.joining(","));
		return cartProducts;
	}

}
