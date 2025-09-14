package io.jayaprabahar.camunda.ecommerce.sellernotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Seller notification service application.
 *
 * <p>Example microservice responsible for notifying sellers about order
 * lifecycle events. Replace the sample implementation with production-ready
 * integrations (messaging, email, push) and proper error handling.
 */
@SpringBootApplication
public class SellerNotificationServiceApplication {

	/**
	 * Application entry point.
	 *
	 * @param args standard Spring Boot args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SellerNotificationServiceApplication.class, args);
	}

}
