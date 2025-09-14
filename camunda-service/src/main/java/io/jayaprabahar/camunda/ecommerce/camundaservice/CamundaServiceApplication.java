package io.jayaprabahar.camunda.ecommerce.camundaservice;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Camunda orchestration service application.
 *
 * <p>This Spring Boot application boots the Zeebe/Camunda client and deploys
 * BPMN workflow definitions found on the classpath. It serves as the central
 * orchestrator for the example e-commerce checkout workflows in this
 * repository.
 */
@SpringBootApplication
@EnableZeebeClient
@Deployment(resources = "classpath*:/bpmn/**/*.bpmn")
public class CamundaServiceApplication {

	/**
	 * Application entry point.
	 *
	 * @param args standard Spring Boot args
	 */
	public static void main(String[] args) {
		SpringApplication.run(CamundaServiceApplication.class, args);
	}

}
