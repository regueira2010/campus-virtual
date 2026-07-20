# Campus Virtual - Core Domain

Campus Virtual is an e-learning platform. This repository contains the Pure Domain Core (Core de Entidades de Dominio Puro), completely isolated from any frameworks, databases, or external interfaces, following the principles of Clean Architecture / Hexagonal Architecture.

## Architecture Highlights

* **Pure Java:** No Spring, JPA, or web annotations. The domain depends only on itself.
* **Dependency Inversion:** All external interactions (such as notifications) are modeled as interfaces (`NotificationService`) and injected via constructors.
* **English Nomenclature:** Clean, modular code entirely in English.

## Testing & Quality Assurance

This project uses JUnit 5 and Mockito to ensure the highest standards of quality:

* **Rigorous AAA Pattern:** All tests are strictly structured using Arrange, Act, and Assert phases.
* **Business Exceptions:** Custom domain exceptions (`InvalidTitleException`, `InvalidContentTitleException`, `InvalidModuleTitleException`) are verified thoroughly using `assertThrows`.
* **100% Coverage:** The test suite guarantees 100% Line and Branch coverage, ensuring no orphan logic exists across all domain entities (`Course`, `Module`, `Content`, `Progress`).

## How to Verify

To run the automated tests and generate the JaCoCo coverage report, execute the following command in the root of the project:

```bash
mvn clean test jacoco:report
