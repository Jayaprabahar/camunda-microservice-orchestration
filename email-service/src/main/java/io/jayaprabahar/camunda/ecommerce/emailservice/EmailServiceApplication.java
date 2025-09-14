package io.jayaprabahar.camunda.ecommerce.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Email service application.
 *
 * <p>Example Spring Boot microservice responsible for sending transactional
 * emails as part of the e-commerce checkout process. In production replace the
 * sample implementations with real email delivery providers and credentials
 * stored in a secure secrets manager.
 */
@SpringBootApplication
public class EmailServiceApplication {

	/**
	 * Application entry point.
	 *
	 * @param args standard Spring Boot args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}

}
