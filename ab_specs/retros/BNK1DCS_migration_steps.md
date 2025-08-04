---
description: Steps used to migrate BNK1DCS COBOL program to Java Spring Boot
---

# BNK1DCS (Display / Delete / Update Customer Screen) – Migration Plan

> BNK1DCS is a multi-function BMS screen that (1) displays customer details, (2) deletes a customer when **PF5** is pressed, and (3) allows editing/updating when **PF10** is pressed. The Java migration must expose equivalent functionality via REST APIs and the existing service layer.

## 1. Discovery & Classification
1. Review `BNK1DCS.cbl`, copybook `BNK1DCM`, and downstream programs `INQCUST`, `DELCUS`, `UPDCUST` invoked through COMMAREAs.
2. Classify as **Screen/BMS Program ➜ REST Controller + DTOs**.

## 2. Functional Decomposition
| COBOL Action | Java Equivalent |
|--------------|-----------------|
| Display customer | `GET /api/customers/{id}` |
| Delete customer (PF5) | `DELETE /api/customers/{id}` |
| Update customer (PF10) | `PUT /api/customers/{id}` |

## 3. DTO & Validation Mapping
1. Map COMMAREA fields to:
   - `CustomerResponseDto` (read-only fields)
   - `CustomerUpdateRequestDto` (editable fields: address, credit score, etc.).
2. Encode validation (field lengths, numeric ranges, date formats).

## 4. Java Artifact Scaffold
1. Execute workflow `/one-migration-scaffold` with module **BNK1DCS** to generate:
   - `CustomerController` (if not already present) or extend existing.
   - `CustomerUpdateRequestDto`.
   - Service interface `CustomerMaintenanceService` with methods `getCustomer`, `updateCustomer`, `deleteCustomer`.
2. Place classes under `com.cbsa.migration.*` packages.

## 5. Service Implementation
1. Implement `CustomerMaintenanceService` using `JdbcCustomerRepository`.
2. Ensure delete is **logical delete** if required by schema (set `logically_deleted = 1`).
3. Wrap operations in `@Transactional` blocks.

## 6. Controller Layer
1. Define endpoints:
   - `@GetMapping("/api/customers/{id}")`
   - `@PutMapping("/api/customers/{id}")`
   - `@DeleteMapping("/api/customers/{id}")`
2. Use `@Valid` for update requests; return appropriate HTTP status codes.
3. Handle `CustomerNotFoundException` → `404`.

## 7. Repository Enhancements
1. Add `findById`, `deleteLogical`, `updateCustomer` methods to `JdbcCustomerRepository` if missing.
2. Add unit tests for new queries (H2 compat).

## 8. Testing Strategy
1. **Unit**: DTO validation + repository SQL.
2. **Service**: Happy path, not-found, validation failure.
3. **Controller Integration**: MockMVC covering all endpoints & error paths.
4. Target ≥90% coverage on new code; overall project moves toward 50% threshold.

## 9. Documentation & OpenAPI
1. Annotate endpoints/DTOs with SpringDoc annotations.
2. Confirm Swagger UI shows new operations.

## 10. SonarQube & Quality Gate
1. Run `mvn clean test jacoco:report sonar:sonar` in `java-migration`.
2. Fix any new issues; ensure quality gate passes.

## 11. Retrospective & Cleanup
1. Save this migration plan in `specs/retros/BNK1DCS_migration_steps.md` (done ✔).
2. Update main migration list & Jira ticket (`CJM-XXX`).
3. Conduct retro to record lessons and adjust future scaffolds.
