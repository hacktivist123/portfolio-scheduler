package org.inits.inits_portfolio_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InitsPortfolioSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitsPortfolioSchedulerApplication.class, args);
	}

}
