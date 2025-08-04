# CREACC Java Migration Scaffold

## COBOL Program Analysis
**Program**: CREACC.cbl (Account Creation)  
**Purpose**: Creates new customer accounts with validation, atomic numbering, and transaction logging  
**Complexity**: Medium-High (business logic + data access + transaction management)

## Architecture Impact Assessment

Based on analysis of `CREACC.cbl` and existing Java migration patterns, this program impacts:

### Current Architecture Touchpoints
- **Model**: Account.java (exists)
- **Repository**: AccountRepository.java + JdbcAccountRepository.java (exist)
- **Repository**: ControlRepository.java + JdbcControlRepository.java (exist) 
- **Repository**: TransactionRepository.java + JdbcTransactionRepository.java (exist)
- **Service**: No existing AccountService yet
- **Controller**: No existing AccountController yet
- **DTOs**: No Account creation DTOs exist yet

### Missing Dependencies Check
❌ **MapStruct**: Not in pom.xml - would be helpful for DTO mapping  
❌ **Lombok**: Not in pom.xml - but existing code uses it (investigate)  
❌ **Spring Data JPA**: Not in pom.xml - using JDBC pattern instead (consistent with existing)

## Migration Scaffold Table

| Piece | COBOL paragraph(s) | Java artifact(s) | Primary inputs | Primary outputs | External touch-points |
|-------|-------------------|------------------|---------------|----------------|----------------------|
| **Customer Validation** | P010 (INQCUST link) | AccountService.validateCustomer() | Customer number | Valid/Invalid flag | CustomerRepository |
| **Account Limit Check** | P010 (INQACCCU link) | AccountService.checkAccountLimit() | Customer number | Account count, Valid flag | AccountRepository |
| **Account Type Validation** | ACCOUNT-TYPE-CHECK | AccountService.validateAccountType() | Account type string | Valid/Invalid flag | None (enum validation) |
| **Account Number Generation** | ENQ-NAMED-COUNTER, FIND-NEXT-ACCOUNT, DEQ-NAMED-COUNTER | AccountService.generateAccountNumber() | Sort code | New account number | ControlRepository |
| **Account Creation** | WRITE-ACCOUNT-DB2 | AccountService.createAccount() | Account data | Created account | AccountRepository |
| **Transaction Logging** | WRITE-PROCTRAN | AccountService.createAccount() | Account details | Transaction record | TransactionRepository |
| **Date Calculations** | CALCULATE-DATES | AccountService.calculateStatementDates() | Opening date | Last/Next statement dates | None (utility logic) |
| **REST API Endpoint** | Main PREMIERE section | AccountController.createAccount() | HTTP request | HTTP response with account | AccountService |
| **Request DTO** | DFHCOMMAREA input | AccountCreateRequestDto | HTTP JSON | Validated account data | None (validation annotations) |
| **Response DTO** | DFHCOMMAREA output | AccountCreateResponseDto | Account entity | HTTP JSON response | None (data mapping) |
| **Error Handling** | GET-ME-OUT-OF-HERE, ABNDPROC calls | Custom exceptions + @ControllerAdvice | Various failure scenarios | HTTP error responses | Logging framework |

## Clarification Questions Needed

**1. Account Creation API Design:** Should this be:
   - `POST /api/accounts` (RESTful create account)
   - `POST /api/customers/{customerId}/accounts` (nested resource)

**2. Named Counter System:** The COBOL uses CICS ENQ/DEQ for atomic counter increments. Should I:
   - Use database-level atomic operations (SELECT FOR UPDATE)
   - Implement application-level locking with `@Transactional`
   - Create a dedicated counter service

**3. Account Number Generation:** The COBOL generates 8-digit account numbers from a counter. Should I:
   - Keep the same format and logic
   - Use database auto-increment  
   - Use UUIDs for modern design

**4. Error Handling:** The COBOL has extensive error handling with ABNDPROC calls. Should I:
   - Create custom exceptions for each failure code
   - Use standard HTTP status codes
   - Integrate with the existing ABNDPROC service

**5. Transaction Integration:** The program writes to both ACCOUNT and PROCTRAN tables. Should I:
   - Use `@Transactional` to ensure atomicity
   - Create separate transaction records
   - Handle rollback scenarios

## Output Summary

### Open Questions
The clarification questions above need answers before proceeding to implementation. Key decisions impact:
- API endpoint design patterns
- Database concurrency handling
- Account number generation strategy
- Error handling approach
- Transaction management patterns

### Foreseen Gotchas
1. **Atomic Counter Logic**: COBOL's ENQ/DEQ pattern needs careful translation to avoid race conditions
2. **Date Calculations**: Complex leap year logic for statement dates needs exact preservation
3. **Two-Phase Transactions**: Account creation + transaction logging must be atomic
4. **Error Code Mapping**: COBOL fail codes (1-9) need translation to HTTP status codes
5. **Account Type Validation**: Hardcoded validation logic needs to be maintainable
6. **Integration Dependencies**: Requires CustomerService (INQCUST) and account counting logic (INQACCCU)

### Important Flags for Implementation
- **Medium-High Complexity**: This is not a simple CRUD operation
- **Transaction Management Required**: Must ensure ACID properties
- **Multiple Repository Interactions**: Account, Control, Transaction repositories needed
- **Business Logic Heavy**: Significant validation and calculation logic
- **Integration Points**: Depends on customer validation services
- **Database Consistency Critical**: Account numbering must be thread-safe

### Next Steps
Once clarification questions are answered:
1. Create AccountService with business logic methods
2. Create AccountController with REST endpoints
3. Create AccountCreate DTOs with validation
4. Extend existing repositories as needed
5. Implement comprehensive error handling
6. Add integration tests for transaction scenarios
