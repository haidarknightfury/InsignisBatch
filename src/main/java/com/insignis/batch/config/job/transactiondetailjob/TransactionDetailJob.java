package com.insignis.batch.config.job.transactiondetailjob;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.insignis.batch.model.loadproductjob.TransactionDataDTO;
import com.insignis.batch.model.transactionjob.Cart;

@Configuration
public class TransactionDetailJob {

	private MongoTemplate mongoTemplate;
	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	public TransactionDetailJob(MongoTemplate mongoTemplate, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.mongoTemplate = mongoTemplate;
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	@Bean
	public MongoItemReader<Cart> transactionCartItemReader() {
		MongoItemReader<Cart> reader = new MongoItemReader<Cart>();
		reader.setTemplate(mongoTemplate);
		reader.setSort(new HashMap<String, Sort.Direction>() {
			private static final long serialVersionUID = 1L;
			{
				put("_id", Direction.DESC);
			}
		});
		reader.setTargetType(Cart.class);
		reader.setQuery("{}");
		return reader;
	}

	@Bean
	public ItemProcessor<Cart, List<TransactionDataDTO>> cartItemProcessor() {
		return new TransactionDetailItemProcessor();
	}

	@Bean
	public ItemWriter<TransactionDataDTO> transactionItemWriter() throws Exception {
		FlatFileItemWriter<TransactionDataDTO> csvItemWriter = new FlatFileItemWriter<TransactionDataDTO>();
		// "InvoiceNo,StockCode,Description,Quantity,InvoiceDate,UnitPrice,CustomerID,Country";
		String outputPath = File.createTempFile("cartOutput", ".csv").getAbsolutePath();
		System.out.println(">> Output path : " + outputPath);
		csvItemWriter.setResource(new FileSystemResource(outputPath));
		csvItemWriter.setLineAggregator(transactionLineAggregator());
		csvItemWriter.afterPropertiesSet();
		return csvItemWriter;
	}

	@Bean
	public ItemWriter<List<TransactionDataDTO>> listTransactionItemWriter() throws Exception {
		ListUnpackingItemWriter<TransactionDataDTO> listUnpackingItemWriter = new ListUnpackingItemWriter<>();
		listUnpackingItemWriter.setDelegate(transactionItemWriter());
		listUnpackingItemWriter.afterPropertiesSet();
		return listUnpackingItemWriter;
	}

	private LineAggregator<TransactionDataDTO> transactionLineAggregator() {
		DelimitedLineAggregator<TransactionDataDTO> lineAggregator = new DelimitedLineAggregator<TransactionDataDTO>();
		lineAggregator.setDelimiter(",");

		FieldExtractor<TransactionDataDTO> fieldExtractor = transctionFieldExtractor();
		lineAggregator.setFieldExtractor(fieldExtractor);
		return lineAggregator;
	}

	private FieldExtractor<TransactionDataDTO> transctionFieldExtractor() {
		BeanWrapperFieldExtractor<TransactionDataDTO> extractor = new BeanWrapperFieldExtractor<TransactionDataDTO>();
		extractor.setNames(new String[] { "InvoiceNo", "StockCode", "Description", "Quantity", "InvoiceDate", "UnitPrice", "CustomerID", "Country" });
		return extractor;
	}

	@Bean
	public Step transactionDetailStep() throws Exception {
		// @formatter:off
		return stepBuilderFactory.get("transactionDetailStep")
				.<Cart, List<TransactionDataDTO>>chunk(10).reader(transactionCartItemReader())
				.processor(cartItemProcessor())
				.writer(listTransactionItemWriter())
				.build();
		// @formatter:on
	}

	@Bean
	public Job transactionDetailsJob() throws Exception {
		// @formatter:off
		return jobBuilderFactory.get("transactionDetailJob").start(transactionDetailStep()).build();
		// @formatter:on
	}

}
