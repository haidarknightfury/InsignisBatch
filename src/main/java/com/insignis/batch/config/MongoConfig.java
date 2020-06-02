package com.insignis.batch.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.insignis.batch")
public class MongoConfig implements InitializingBean {

	private MappingMongoConverter mappingMongoConverter;

	@Autowired
	public MongoConfig(MappingMongoConverter mappingMongoConverter) {
		this.mappingMongoConverter = mappingMongoConverter;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
	}

}