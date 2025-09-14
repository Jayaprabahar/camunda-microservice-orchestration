# camunda-ecommerce
An ecommerce project explains process based microservice orchestration using camunda

# camunda-microservice-orchestration

Enterprise-ready reference implementation demonstrating process-driven microservice orchestration using Camunda (Zeebe) and Spring Boot.

Overview
- **Purpose:** Reference architecture showing how to orchestrate an e-commerce checkout workflow across small, focused microservices using Camunda Cloud/Zeebe and Spring Boot.
- **Key capabilities:** process orchestration, worker tasks, inter-service REST communication, event-driven activities, simple examples for email, inventory and seller notifications.

Repository Structure
- `camunda-service/` — Zeebe/Camunda orchestration service and workflow definitions (`bpmn/` and forms).
- `common/` — shared DTOs and utilities (e.g. `ECommerceWebClient`).
- `email-service/` — example microservice that sends transactional emails.
- `inventory-service/` — example microservice that manages inventory state.
- `seller-notification-service/` — service that notifies sellers (e.g. via SNS/SQS or email).

Getting Started
Prerequisites
- Java 17+
- Maven 3.6+
- A running Zeebe broker (Camunda Cloud or local Docker image) if you want to run the orchestration.

Build
Clone the repository and build artifacts with Maven:

```powershell
git clone <repo-url>
cd camunda-microservice-orchestration
mvn clean package -DskipTests
```

Run (local)
Each service is a Spring Boot application. Run from the module directories, for example:

```powershell
cd camunda-service
mvn spring-boot:run
```

Configuration
- Application configuration is stored in `src/main/resources/application.yml` for each service. Use environment variables or an external config server for production deployments.

Architecture and Design
- Workflows are defined as BPMN files under `camunda-service/src/main/resources/bpmn/`.
- Worker logic and REST endpoints live in the respective microservice modules. Services communicate over HTTP and (optionally) async messaging.

CI/CD
- This repository is intended to be integrated into enterprise CI pipelines. Recommended steps:
	- Run `mvn -T 1C -DskipTests=false test` to run tests in parallel.
	- Build artifacts and publish to an internal Maven registry.
	- Package services into Docker images and push to a secure registry.
	- Deploy via Kubernetes manifests or Helm charts, with appropriate secrets management.

Security
- Do not store secrets in VCS. Use a secrets manager (HashiCorp Vault, AWS Secrets Manager, Azure Key Vault).
- Enable TLS for inbound/outbound traffic and authenticate/authorize service-to-service calls.

Contributing
- File issues or feature requests using the project's issue tracker.
- Fork the repo, create a feature branch named `feature/<short-description>`, make changes, run tests, and open a pull request describing the change.

License
- Add the appropriate enterprise license or reference legal guidelines for usage.

Contact
- For questions about the reference implementation, open an issue or contact the project maintainers.

