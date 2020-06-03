package com.insignis.batch.config.job.transactiondetailjob;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class ListUnpackingItemWriter<T> implements ItemWriter<List<T>>, ItemStream, InitializingBean {

	private ItemWriter<T> delegate;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(delegate, "delegate must be set");
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if (delegate instanceof ItemStream) {
			((ItemStream) delegate).open(executionContext);
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		if (delegate instanceof ItemStream) {
			((ItemStream) delegate).update(executionContext);
		}
	}

	@Override
	public void close() throws ItemStreamException {
		if (delegate instanceof ItemStream) {
			((ItemStream) delegate).close();
		}
	}

	@Override
	public void write(List<? extends List<T>> items) throws Exception {
		final List<T> consolidatedList = new ArrayList<>();
		for (final List<T> list : items) {
			consolidatedList.addAll(list);
		}
		delegate.write(consolidatedList);
	}

	public void setDelegate(ItemWriter<T> delegate) {
		this.delegate = delegate;
	}

}
