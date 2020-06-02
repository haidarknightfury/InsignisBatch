package com.insignis.batch.config.job.loadproductjob;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.insignis.batch.model.loadproductjob.TransactionDataDTO;

public class TransactionFieldSetMapper implements FieldSetMapper<TransactionDataDTO> {

	private static final int DOLLAR_TO_MUR = 35;

	@Override
	public TransactionDataDTO mapFieldSet(FieldSet fieldSet) throws BindException {
		TransactionDataDTO transaction = new TransactionDataDTO();
		transaction.setDescription(fieldSet.readString("Description"));
		transaction.setStockCode(fieldSet.readString("StockCode"));
		BigDecimal price = new BigDecimal(fieldSet.readFloat("UnitPrice") * DOLLAR_TO_MUR);
		price = price.setScale(2, RoundingMode.HALF_UP);
		transaction.setUnitPrice(price.floatValue());
		return transaction;
	}

}
