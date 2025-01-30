package com.investment_aggregator.investment_aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InvestmentAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestmentAggregatorApplication.class, args);
	}

}
