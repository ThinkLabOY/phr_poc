package org.ech.phr;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicates;

import lombok.Data;

@SpringBootApplication
@EnableSwagger2
@Configuration
public class PhrPocApplication {

	public static String hbaseZookeeperQuorom;

	@Value( "${hbase.zookeeper.quorum}" )
	public void setHbaseZookeeperQuorom(String hbaseZookeeperQrm) {
		hbaseZookeeperQuorom = hbaseZookeeperQrm;
	}

	@Bean
	public Docket api() { 
		return new Docket(DocumentationType.SWAGGER_2)  
				.select()                                  
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())                          
				.paths(Predicates.not(PathSelectors.regex("/error.*")))
				.build();                                           
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public ObjectMapper memplate() {
		return new ObjectMapper();
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PhrPocApplication.class, args);
	}

}
