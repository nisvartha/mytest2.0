package org.nisvartha.reactive.website;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@SpringBootApplication
public class WebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
		System.out.println("Hello Webflux");
		
		
	}

}







