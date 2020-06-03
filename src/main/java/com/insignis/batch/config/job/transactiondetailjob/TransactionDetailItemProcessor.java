package com.insignis.batch.config.job.transactiondetailjob;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.insignis.batch.model.loadproductjob.TransactionDataDTO;
import com.insignis.batch.model.transactionjob.Cart;

public class TransactionDetailItemProcessor implements ItemProcessor<Cart, List<TransactionDataDTO>> {

	@Override
	public List<TransactionDataDTO> process(Cart item) throws Exception {
		List<TransactionDataDTO> transactions = new ArrayList<TransactionDataDTO>();
		item.getProducts().forEach(product -> {
			TransactionDataDTO transactionDataDTO = new TransactionDataDTO();
			transactionDataDTO.setCountry(item.getOutlet());
			transactionDataDTO.setCustomerID(item.getCustomerId());
			transactionDataDTO.setDescription(product.getName());
			transactionDataDTO.setInvoiceDate(item.getDate());
			transactionDataDTO.setInvoiceNo(item.getId());
			transactionDataDTO.setQuantity(product.getQuantity());
			transactionDataDTO.setStockCode(product.getId());
			transactionDataDTO.setUnitPrice(product.getUnitPrice().floatValue());
			transactions.add(transactionDataDTO);
		});
		return transactions.isEmpty() ? null : transactions;
	}

}
