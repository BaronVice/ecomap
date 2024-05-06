package com.eco.ecomap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 TODO:
  HUGE WARNING - double may fuck u up, if it will - set axis in unsigned long and when needed parse to double
 */

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties()
public class EcomapApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomapApplication.class, args);
	}

}
