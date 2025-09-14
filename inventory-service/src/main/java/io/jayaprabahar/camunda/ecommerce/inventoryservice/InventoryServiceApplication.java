package io.jayaprabahar.camunda.ecommerce.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Inventory service application.
 *
 * <p>Example Spring Boot microservice managing product inventory used by the
 * orchestration workflow. Production deployments should integrate with a
 * durable datastore and implement reconciliation, metrics and alerts.
 */
@SpringBootApplication
public class InventoryServiceApplication {

	/**
	 * Application entry point.
	 *
	 * @param args standard Spring Boot args
	 */
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
