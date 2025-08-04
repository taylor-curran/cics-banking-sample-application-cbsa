# COBOL-to-Java Migration Retro Synthesis

## Generalizable Migration Steps

Based on analysis of BNK1CAC, BNK1CRA, and BNK1DCS migration retros, here are the proven patterns that work for migrating COBOL programs to our Spring Boot architecture:

## 🎯 **Universal Migration Workflow**

### **Phase 1: Discovery & Classification**
1. **Locate Source Files**: Find `.cbl` program, associated copybooks, and linked programs
2. **Classify Program Type**: Apply migration decision criteria
   - **Screen/BMS Programs** → REST Controller + DTOs
   - **Business Logic Programs** → Service Classes  
   - **Utility Programs** → Service/Utility Classes
3. **Identify Dependencies**: Map COBOL program linkages to Java service calls

### **Phase 2: Data & API Design**
1. **Extract Business Rules**: Document validation logic, field constraints, business flows
2. **Map Data Structures**: COBOL fields → DTO properties with validation annotations
3. **Define API Contracts**: 
   - Request DTOs (input validation)
   - Response DTOs (clean API output)
   - HTTP methods and status codes

### **Phase 3: Java Artifact Scaffold**
1. **Run Scaffold Workflow**: Execute `/one-migration-scaffold` with module name
2. **Generate Artifacts**: Controller, DTOs, Service interface, implementation stubs
3. **Maintain Package Structure**: Keep `com.cbsa.migration.*` hierarchy consistent

### **Phase 4: Service Implementation**
1. **Implement Business Logic**: Translate COBOL program logic to Java service methods
2. **Use Repository Pattern**: Leverage existing `JdbcXxxRepository` implementations
3. **Add Transaction Management**: Use `@Transactional` for atomic operations
4. **Handle Error Cases**: Map COBOL error conditions to appropriate exceptions

### **Phase 5: Controller Layer**
1. **Define REST Endpoints**: Map COBOL functions to HTTP operations
2. **Add Validation**: Use `@Valid` on request DTOs with Bean Validation
3. **Return Proper Status Codes**: 
   - `200 OK` for successful queries
   - `201 Created` for successful creates
   - `400 Bad Request` for validation failures
   - `404 Not Found` for missing resources
   - `422 Unprocessable Entity` for business rule violations

### **Phase 6: Repository Enhancements**
1. **Extend Existing Repositories**: Add methods needed by new services
2. **Maintain JDBC Pattern**: Interface + JDBC implementation for testability
3. **Handle Composite Keys**: Use existing patterns for sort_code + number combinations
4. **Support Logical Deletes**: Use `logically_deleted` flags where appropriate

### **Phase 7: Testing Strategy**
1. **Unit Tests**: DTO validation, service logic, repository methods
2. **Integration Tests**: Controller + H2 database for end-to-end validation
3. **Coverage Target**: ≥90% on new code, move project toward 50% overall
4. **Test Isolation**: Use H2 in-memory database with compatible schema

### **Phase 8: Documentation & Quality**
1. **OpenAPI Annotations**: Document endpoints and DTOs with SpringDoc
2. **Swagger Verification**: Confirm new endpoints appear in `/swagger-ui/index.html`
3. **SonarQube Analysis**: Run `mvn clean test jacoco:report sonar:sonar`
4. **Quality Gate**: Resolve any new issues before completion

### **Phase 9: Retrospective & Tracking**
1. **Document Migration**: Save steps in `ab_specs/retros/[PROGRAM]_migration_steps.md`
2. **Update Master Plan**: Track progress in main migration documentation
3. **Jira Integration**: Link to project tickets (CJM-XXX, TDP-XXX)
4. **Capture Lessons**: Record what worked and what to improve

## 🏗️ **Proven Architecture Patterns**

### **Controller Pattern**
```java
@RestController
@RequestMapping("/api/[entity]")
public class [Entity]Controller {
    private final [Entity]Service service;
    
    @PostMapping
    public ResponseEntity<[Entity]ResponseDto> create(@Valid @RequestBody [Entity]RequestDto request) {
        // Implementation
    }
}
```

### **Service Pattern**
```java
@Service
@Transactional
public class [Entity]Service {
    private final [Entity]Repository repository;
    
    public [Entity]ResponseDto process([Entity]RequestDto request) {
        // Business logic implementation
    }
}
```

### **Repository Pattern**
```java
public interface [Entity]Repository {
    Optional<[Entity]> findById(String sortCode, Long id);
    [Entity] save([Entity] entity);
    boolean deleteById(String sortCode, Long id);
}

@Repository
public class Jdbc[Entity]Repository implements [Entity]Repository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<[Entity]> rowMapper = // Implementation
}
```

## 🎯 **What Works: Key Success Factors**

### **1. Consistent Package Structure**
- ✅ **Single Package Hierarchy**: `com.cbsa.migration.*` (no duplicates)
- ✅ **Layer Separation**: controller, service, repository, model, dto packages
- ✅ **Naming Conventions**: Clear, consistent class and method names

### **2. Repository Pattern Excellence**
- ✅ **Interface + JDBC Implementation**: Enables easy testing with H2
- ✅ **Composite Key Handling**: Consistent patterns for sort_code + number
- ✅ **Row Mapper Reuse**: Clean, testable data mapping
- ✅ **Upsert Logic**: Check existence, then INSERT or UPDATE

### **3. DTO Design Principles**
- ✅ **Request/Response Separation**: Clear API contracts
- ✅ **Bean Validation**: Declarative validation with annotations
- ✅ **Business Rule Encoding**: Validation matches COBOL constraints
- ✅ **Clean JSON**: Hide internal database fields from API

### **4. Service Layer Design**
- ✅ **Single Responsibility**: Each service handles one business domain
- ✅ **Transaction Management**: `@Transactional` for atomic operations
- ✅ **Repository Delegation**: Services orchestrate, repositories persist
- ✅ **Error Handling**: Meaningful exceptions for business rule violations

### **5. Testing Framework**
- ✅ **H2 In-Memory**: Fast, isolated test execution
- ✅ **Schema Consistency**: Same structure as production, H2-compatible syntax
- ✅ **Layer Testing**: Unit tests for each layer (model, repository, service, controller)
- ✅ **MockMVC Integration**: End-to-end API testing

### **6. Quality Assurance**
- ✅ **SonarQube Integration**: Automated code quality analysis
- ✅ **JaCoCo Coverage**: Measurable test coverage with 50% threshold
- ✅ **OpenAPI Documentation**: Self-documenting APIs with Swagger UI
- ✅ **Build Validation**: Tests must pass before deployment

## ⚠️ **Common Pitfalls to Avoid**

### **1. Architecture Violations**
- ❌ **Duplicate Packages**: Avoid `com.bank.cbsa.*` or similar hierarchies
- ❌ **Duplicate Entities**: Single Account, Customer, Transaction model classes
- ❌ **Complex Patterns**: Keep services simple, repositories straightforward
- ❌ **Hardcoded Paths**: Use Spring `@Value` injection for configuration

### **2. Database Issues**
- ❌ **Schema Drift**: Test schema must match production structure
- ❌ **Syntax Incompatibility**: H2 vs SQLite differences cause test failures
- ❌ **Missing Constraints**: Preserve COBOL data validation in SQL
- ❌ **Composite Key Errors**: Handle sort_code + number combinations correctly

### **3. Testing Problems**
- ❌ **Insufficient Coverage**: <50% coverage fails builds
- ❌ **Brittle Tests**: Tests that break with minor changes
- ❌ **Missing Integration**: Unit tests without end-to-end validation
- ❌ **Schema Mismatches**: Test database different from production

### **4. API Design Issues**
- ❌ **Inconsistent Status Codes**: Wrong HTTP responses for error conditions
- ❌ **Poor Validation**: Missing or incorrect Bean Validation annotations
- ❌ **Exposed Internals**: Database fields leaked through API
- ❌ **Missing Documentation**: Endpoints without OpenAPI annotations

## 🚀 **Next Migration Priorities**

Based on complexity analysis and architectural patterns:

### **Tier 1: Immediate Targets (Simple Utilities)**
1. **ABNDPROC** - Error logging utility (177 lines)
   - Single CICS WRITE operation
   - Simple data structure handling
   - Critical for other program error handling

2. **CRDTAGY1-5** - Credit agency simulators (~274 lines each)
   - Random number generation
   - Can be simplified to mock services
   - Remove timing delays, use simple REST responses

### **Tier 2: Core Business Logic (Medium Complexity)**
3. **Customer CRUD**: CRECUST, INQCUST, UPDCUST, DELCUS
4. **Account CRUD**: CREACC, INQACC, UPDACC, DELACC
5. **Transaction Processing**: XFRFUN, DBCRFUN

### **Tier 3: Complex Programs (Defer Until Later)**
6. **BANKDATA** - Batch data initialization (1,464 lines)
   - Complex VSAM + DB2 operations
   - Heavy file I/O and data generation
   - Save for last when core patterns are stable

## 📊 **Success Metrics**

- ✅ **Functional**: All COBOL business logic preserved in Java
- ✅ **Quality**: >50% test coverage maintained
- ✅ **Architecture**: Consistent Spring Boot patterns
- ✅ **Performance**: Response times within acceptable limits
- ✅ **Documentation**: All APIs documented in Swagger UI
- ✅ **Maintainability**: Clean, testable code following established patterns

---

*This synthesis captures proven patterns from successful BNK1CAC, BNK1CRA, and BNK1DCS migrations. Use these steps as a template for future COBOL program migrations to ensure consistency and quality.*
