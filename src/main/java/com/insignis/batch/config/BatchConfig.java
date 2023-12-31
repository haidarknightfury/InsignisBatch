package com.insignis.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("insignis");
		executor.setMaxPoolSize(12);
		executor.setCorePoolSize(8);
		executor.setQueueCapacity(15);
		return executor;
	}

	@Bean
	public JobLauncher asyncJobLauncher(JobRepository jobRepository) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(threadPoolTaskExecutor());
		return jobLauncher;
	}
}
