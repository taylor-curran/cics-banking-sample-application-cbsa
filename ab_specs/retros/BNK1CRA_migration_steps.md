---
description: Steps used to migrate BNK1CRA COBOL program to Java Spring Boot
---

# BNK1CRA (Credit / Debit Transaction Screen) – Migration Plan

> BNK1CRA is the BMS screen that captures a **single credit or debit** request for a given account and performs amount validation and balance updates. In Java it should be replaced by REST endpoints backed by the existing transaction service & repository layer.

## 1. Discovery & Classification
1. Review `BNK1CRA.cbl`, copybook `BNK1CDM`, and any sub program linkage (likely to `DBCRFUN` or direct DB2 writes).
2. Classify as **Screen/BMS Program ➜ REST Controller + DTOs**.

## 2. Identify Business Rules
1. Fields: `accountNumber`, `sign` (+/-), `amount` (12 digits incl. cents).
2. Validation:
   - Account must exist & be active
   - Amount > 0 & <= overdraft limit (for debits)
   - Convert/display amounts with 2 decimal places
3. Transaction type mapping: `+` → CREDIT, `-` → DEBIT.

## 3. DTO Design
- `TransactionRequestDto { String accountNumber; BigDecimal amount; TransactionType type; }`
- `TransactionResponseDto { String transactionId; BigDecimal newBalance; }`
- Add `enum TransactionType { CREDIT, DEBIT }`.
- Add bean validation annotations (`@Positive`, length, etc.).

## 4. Java Artifact Scaffold
1. Run workflow `/one-migration-scaffold` with module **BNK1CRA** to create:
   - `TransactionController` (if not present) with `POST /api/transactions`.
   - DTOs & `TransactionType` enum.
   - Service interface `TransactionProcessingService`.

## 5. Service Implementation
1. Implement `TransactionProcessingService#process(TransactionRequestDto)` that:
   - Uses `JdbcAccountRepository` & `JdbcTransactionRepository`.
   - Performs validation & balance calculations.
   - Persists `bank_transaction` record and updates `account` balances atomically (`@Transactional`).

## 6. Controller Layer
1. `@PostMapping("/api/transactions")` consumes/produces JSON.
2. On success return `201 Created` + response DTO.
3. Error handling:
   - 404 if account not found
   - 422 for business rule violations (insufficient funds, etc.).

## 7. Repository Enhancements
1. Ensure `JdbcAccountRepository` exposes `updateBalance` with concurrency control (e.g., optimistic locking or `FOR UPDATE`).
2. Ensure `JdbcTransactionRepository` insert method exists.

## 8. Testing Strategy
1. **Unit Tests**: DTO validation, service logic (credit, debit, edge cases).
2. **Integration Tests**: Controller + H2 verifying DB state changes.
3. Mock PostgreSQL/SQLite concurrency where needed.
4. Target ≥90% coverage on new code.

## 9. Documentation & OpenAPI
1. Annotate DTOs & controller for SpringDoc.
2. Verify `/swagger-ui/index.html` shows `/api/transactions`.

## 10. Sonar & Quality Gate
1. Run `mvn clean test jacoco:report sonar:sonar` (coverage <50%) to ensure no new issues.

## 11. Retrospective & Tracking
1. Save plan in `specs/retros/BNK1CRA_migration_steps.md` (done ✔).
2. Update main migration list & Jira ticket (`CJM-XXX`).
3. Document lessons for amount parsing & concurrency handling.
