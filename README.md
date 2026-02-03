# Devices API

This was a recruitment task for one of the companies, leaving it as public as a personal playground 

A production-ready RESTful API built with **Java 21** and **Spring Boot 3** for managing device resources. This project provides full CRUD capabilities with domain-specific business logic and a containerized PostgreSQL database.

## Features
* **Device Management**: Create, Read, Update (Full/Partial), and Delete devices.
* **Advanced Filtering**: Fetch devices by **brand** or **state**. Implemented using Spring Data Specifications, allowing for easy extension to complex queries (e.g., date ranges or multi-column search) without modifying repository interfaces.
* **Pagination & Sorting**: Efficiently fetch all devices with built-in Spring Data pagination.
* **Domain Validation**:
    * **Immutability**: Creation time cannot be updated once a device is persisted.
    * **State-Locked Updates**: Name and Brand properties cannot be updated if the device is currently `IN_USE`.
    * **Protected Deletion**: Devices in the `IN_USE` state are protected from deletion to prevent data loss.

## Tech Stack
* **Java 21**
* **Spring Boot 3.5** (Web, Data JPA, Validation, Actuator)
* **PostgreSQL**
* **Testcontainers** (For reliable integration testing)
* **Docker & Docker Compose** (Containerization)
* **Swagger/OpenAPI 3** (Interactive API Documentation)
* **Flyway** (Database version control and migration management)
* **Gradle 8**

## Future Improvements & Production Readiness
Due to the time constraints of this challenge, the following enhancements are recommended for a true production-grade deployment:
- **Caching**: Implementation of a caching layer (e.g., Redis or Caffeine) for the findAll and fetchByBrand endpoints to reduce databas e load and improve response latency for frequently accessed device lists.
- **Observability (Tracing)**: Integration with distributed tracing tools like OpenTelemetry or Jaeger (commonly used in environments like OpenShift or Kubernetes) to track request flows across microservices.
- **Monitoring**: Expanding the current Spring Boot Actuator setup to export metrics to Prometheus and Grafana for real-time performance dashboards and alerting.
- **Advanced Logging**: Implementation of structured JSON logging and a centralized log aggregation strategy (e.g., ELK Stack) to improve auditability and debugging in distributed environments.
- **Security**: Addition of Spring Security with OAuth2/JWT to protect endpoints, as current access is open for demonstration purposes.
- **(Small) structural changes**: for example introducing Hexagonal Architecture
- **Linter/test validator**
- **CI/CD pipeline**
- **Concurrency Control (Microservice Readiness)**: Implementation of Optimistic/Pessimistic Locking to handle concurrent updates gracefully.


## Business logic assumptions

Based on the provided document, the following business cases should be discussed further in the UPDATE logic:
- When IN_USE device's state is updated, can we update the name or brand in the same request, or should that happen after state was successfully changed?
- When we change status to IN_USE, can we at the same time change the name and/or brand?

## Getting Started

### Prerequisites
* Docker and Docker Compose
* Java 21+ (if running locally)

### Running with Docker or in your favorite IDE
The application is fully containerized. To spin up the API and the PostgreSQL database:
```bash
cd docker
docker-compose up -d
```
Or run in  your favorite IDE (for example IntelliJ) with `local` profile

Access Swagger Documentation available at: http://localhost:8080/swagger-ui/index.html

## Development Process & Branching Strategy

To maintain a "production-ready" workflow and ensure granular, traceable changes, this project followed a feature-branching strategy. Each task was developed in an isolated branch before being merged into the main line.

### Branching Convention
* `chore/`: Infrastructure, configuration, and boilerplate.
* `feat/`: New functional requirements and business logic.
* `test/`: Test suite implementation and coverage improvements.

### Task Roadmap & Commit History
| Branch     | Task              | Description                                                     |
|:-----------|:------------------|:----------------------------------------------------------------|
| `chore/T0` | **Setup**         | Project initialization, Docker/Postgres config, and Swagger UI. |
| `feat/T1`  | **Domain**        | Entity mapping and Repository layer setup.                      |
| `feat/T2`  | **Creation**      | POST endpoint for device registration.                          |
| `feat/T3`  | **Updates**       | Full and Partial updates with domain validations.               |
| `feat/T4`  | **Retrieval**     | GET endpoint for single resource lookup by ID.                  |
| `feat/T5`  | **Filtering**     | Brand/State filtering using Spring Data Specifications.         |
| `feat/T6`  | **Deletion**      | Logic for secure deletion (protecting `IN_USE` devices).        |
| `test/T7`  | **Testing**       | Integration tests with Testcontainers and Unit testing.         |
| `chore/T8` | **Observability** | Prometheus and Grafana setup                                    |

> **Note:** T7 branching should be test instead of chore, but due to late hour....


