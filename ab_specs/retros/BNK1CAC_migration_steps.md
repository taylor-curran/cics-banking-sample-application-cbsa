---
description: Steps used to migrate BNK1CAC COBOL program to Java Spring Boot
---

# BNK1CAC (Create Account Screen) – Migration Plan

> BNK1CAC is a CICS BMS screen program that validates user-entered account data and, on success, links to COBOL program **CREACC** to create the account record. The Java migration must replicate this behaviour inside the existing Spring Boot architecture while discarding the green-screen UI.

## 1. Discovery & Classification
1. Locate and study `BNK1CAC.cbl`, copybook `BNK1CAM`, and invoked program `CREACC`.
2. Classify as **Screen/BMS Program ➜ REST Controller + DTOs** (see migration decision criteria).

## 2. Data & Validation Mapping
1. List all input fields displayed on BNK1 screen (customer number, account type, interest rate, overdraft limit, etc.).
2. Derive validation rules encoded in COBOL (value ranges, mandatory fields).
3. Map screen fields ➜ `AccountCreationRequestDto` properties.
4. Define `AccountCreationResponseDto` (success flag, new account key, failure reason).

## 3. Java Artifact Scaffold
1. Run workflow `/one-migration-scaffold` (module = **BNK1CAC**) to generate:
   - `AccountCreationController`
   - `AccountCreationRequestDto` / `AccountCreationResponseDto`
   - Service interface `AccountCreationService` + impl stub.
2. Place classes under `com.cbsa.migration.*` respecting package structure.

## 4. Service Implementation
1. Implement `AccountCreationService#createAccount()` that:
   - Delegates to existing `AccountService` / repository layer **or** wraps new `CreAccService` if CREACC logic not yet migrated.
   - Returns DTO with populated fields.
2. Mark method `@Transactional` to ensure atomic creation.

## 5. Controller Layer
1. Expose `POST /api/accounts` endpoint.
2. Use Bean Validation (`@Valid`) on request DTO.
3. Return `201 Created` + response DTO on success.
4. Return `400 Bad Request` with error details on validation failure.

## 6. Repository Adjustments
1. Ensure `JdbcAccountRepository` supports insert with all required columns.
2. Add method `existsByCustomerNumberAndType` for duplicate checking if needed.

## 7. Testing Strategy
1. **Unit Tests**: DTO validation rules.
2. **Service Tests**: Happy path + error scenarios using H2 in-memory DB.
3. **Controller Integration Tests**: MockMVC tests covering status codes & payloads.
4. Achieve ≥90% coverage on new code.

## 8. OpenAPI & Documentation
1. Annotate controller & DTOs with SpringDoc annotations.
2. Verify new endpoints appear in Swagger UI (`/swagger-ui/index.html`).

## 9. SonarQube & Quality Gate
1. Run `mvn clean test jacoco:report sonar:sonar` (coverage <50% stage).
2. Confirm quality gate passes; resolve new issues.

## 10. Retrospective & Cleanup
1. Add migration notes to `specs/retros/BNK1CAC_migration_steps.md` (this file).
2. Update master migration plan and long list.
3. Commit code & open PR; link to Jira (`CJM-XXX`).
4. Hold retro meeting to capture lessons.
