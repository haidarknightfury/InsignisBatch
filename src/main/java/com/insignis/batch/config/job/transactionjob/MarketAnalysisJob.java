package com.insignis.batch.config.job.transactionjob;

import java.io.File;
import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.insignis.batch.model.transactionjob.Cart;

@Configuration
public class MarketAnalysisJob {

	private MongoTemplate mongoTemplate;
	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	public MarketAnalysisJob(MongoTemplate mongoTemplate, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.mongoTemplate = mongoTemplate;
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	@Bean
	public FlatFileItemWriter<Cart> cartItemWriter() throws Exception {
		FlatFileItemWriter<Cart> writer = new FlatFileItemWriter<Cart>();
		writer.setLineAggregator(new CartLineAggregator());
		String outputPath = File.createTempFile("cartOutput", ".csv").getAbsolutePath();
		System.out.println(">> Output path : " + outputPath);
		writer.setResource(new FileSystemResource(outputPath));
		writer.afterPropertiesSet();
		return writer;
	}

	@Bean
	public MongoItemReader<Cart> cartItemReader() {
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
	public Step marketBasketStep() throws Exception {
		return stepBuilderFactory.get("marketBasketStep").<Cart, Cart>chunk(10).reader(cartItemReader()).writer(cartItemWriter()).build();
	}

	@Bean
	public Job marketBasketJob() throws Exception {
		return jobBuilderFactory.get("marketBasketJob").start(marketBasketStep()).build();
	}

}
