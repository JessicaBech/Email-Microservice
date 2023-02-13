package com.tacticalreport.emailMicroservice;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Log4j2
public class EmailMicroserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(EmailMicroserviceApplication.class, args);
		log.info("The Application has Started");
	}

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info()
						.title("Email Microservice API")
						.description("Email Microservice API Documentation "));
	}

}
