package com.insignis.batch.config.job.loadproductjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.insignis.batch.model.loadproductjob.Product;
import com.insignis.batch.model.loadproductjob.TransactionDataDTO;
import com.insignis.batch.repository.loadproductjob.CategoryRepository;
import com.insignis.batch.repository.loadproductjob.SupplierRepository;

@Configuration
public class LoadProductJob {

	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private CategoryRepository categoryRepository;
	private SupplierRepository supplierRepository;
	private MongoTemplate mongoTemplate;

	public LoadProductJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, CategoryRepository categoryRepository, SupplierRepository supplierRepository,
			MongoTemplate mongoTemplate) {

		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.categoryRepository = categoryRepository;
		this.supplierRepository = supplierRepository;
		this.mongoTemplate = mongoTemplate;
	}

	@Bean
	public FlatFileItemReader<TransactionDataDTO> itemReader() {
		FlatFileItemReader<TransactionDataDTO> reader = new FlatFileItemReader<TransactionDataDTO>();
		reader.setLinesToSkip(1); // skipping the header
		reader.setMaxItemCount(2);
		reader.setResource(new ClassPathResource("/data/loadproductjob/retaildata.csv"));

		DefaultLineMapper<TransactionDataDTO> retailDataLineMapper = new DefaultLineMapper<TransactionDataDTO>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setNames(new String[] { "InvoiceNo", "StockCode", "Description", "Quantity", "InvoiceDate", "UnitPrice", "CustomerID", "Country" });
		retailDataLineMapper.setLineTokenizer(tokenizer);
		retailDataLineMapper.setFieldSetMapper(new TransactionFieldSetMapper()); // automatically maps columns
		retailDataLineMapper.afterPropertiesSet();

		reader.setLineMapper(retailDataLineMapper);
		return reader;
	}

	@Bean
	public MongoItemWriter<Product> itemWriter() throws Exception {
		MongoItemWriter<Product> itemWriter = new MongoItemWriter<Product>();
		itemWriter.setTemplate(mongoTemplate);
		itemWriter.setCollection("product");
		itemWriter.afterPropertiesSet();
		return itemWriter;
	}

	@Bean
	public ItemProcessor<TransactionDataDTO, Product> itemProcessor() {
		ItemProcessor<TransactionDataDTO, Product> itemProcessor = new LoadProductItemProcessor(categoryRepository, supplierRepository);
		return itemProcessor;
	}

	@Bean
	public Step loadProductItemStep() throws Exception {
		return stepBuilderFactory.get("loadProductItemStep").<TransactionDataDTO, Product>chunk(5).reader(itemReader()).processor(itemProcessor()).writer(itemWriter()).build();
	}

	@Bean
	public Job loadProductItemJob() throws Exception {
		return jobBuilderFactory.get("loadProductItemJob").start(loadProductItemStep()).build();
	}

}
