# bunny-ddd-framework Development Patterns

> Auto-generated skill from repository analysis

## Overview

This skill covers development patterns for the bunny-ddd-framework, a Java-based Domain-Driven Design framework built with Spring Boot. The framework follows a modular architecture with auto-configuration support and provides various service modules for different functional areas like caching, workflow management, and query DSL support.

## Coding Conventions

### File Naming
- Use **PascalCase** for all Java class files
- Service classes follow the pattern `Abstract*Service.java`
- Auto-configuration classes end with `AutoConfiguration.java`
- Test files use the pattern `*Test.groovy` or `*.test.*`

### Package Structure
```
src/main/java/
├── service/           # Service implementations
├── autoconfigure/     # Spring Boot auto-configuration
└── config/           # Configuration classes

src/main/resources/
├── META-INF/spring.factories  # Auto-configuration registration
└── config/application*.yml    # Application configuration

src/test/
├── groovy/           # Groovy-based tests
└── resources/        # Test configuration files
```

### Import Style
- Mixed import style (both wildcard and specific imports acceptable)
- Group imports logically by package hierarchy

## Workflows

### Initial Commit Fixes
**Trigger:** When bugs or issues are found after initial implementation
**Command:** `/fix-initial`

1. Identify problematic files in the main source tree
2. Apply targeted fixes to specific Java classes
3. Update `pom.xml` dependencies if needed
4. Focus on service layer fixes first
5. Test changes in isolation

**Example Files:**
```
pom.xml
bunny-ddd-*/src/main/java/**/*.java
bunny-ddd-*/src/main/java/**/service/*.java
```

### Add New Service Module
**Trigger:** When adding a new functional area like cache, workflow, or querydsl support
**Command:** `/new-service-module`

1. Create service implementation classes extending abstract base services:
   ```java
   public abstract class AbstractCacheService {
       // Base functionality
   }
   ```

2. Add auto-configuration class:
   ```java
   @Configuration
   @EnableConfigurationProperties
   public class CacheAutoConfiguration {
       // Auto-configuration logic
   }
   ```

3. Register in `META-INF/spring.factories`:
   ```properties
   org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
   com.example.autoconfigure.CacheAutoConfiguration
   ```

4. Update BOM dependencies in `bunny-ddd-bom/pom.xml`

5. Add test configuration files:
   ```yaml
   # src/test/resources/application-test.yml
   spring:
     profiles:
       active: test
   ```

### GitHub Workflow Setup
**Trigger:** When setting up continuous integration or updating build pipeline
**Command:** `/setup-ci`

1. Create `.github/workflows/maven.yml`:
   ```yaml
   name: CI
   on: [push, pull_request]
   jobs:
     test:
       runs-on: ubuntu-latest
       steps:
         - uses: actions/checkout@v2
         - name: Set up JDK
           uses: actions/setup-java@v2
   ```

2. Configure Java version and Maven settings
3. Add code quality tools like SonarQube integration
4. Update root `pom.xml` with necessary plugin configurations
5. Test the workflow with a sample commit

### Update Project Configuration
**Trigger:** When adding new configuration properties or updating deployment settings
**Command:** `/update-config`

1. Update main `pom.xml` with new dependencies or properties
2. Modify application configuration files:
   ```yaml
   # src/main/resources/config/application.yml
   bunny:
     ddd:
       module:
         enabled: true
   ```

3. Update `bunny-ddd-bom/pom.xml` for dependency management
4. Ensure version consistency across all modules
5. Update environment-specific configuration files

### Add Test Infrastructure
**Trigger:** When setting up testing infrastructure for a new module
**Command:** `/setup-tests`

1. Create test configuration classes:
   ```groovy
   @SpringBootTest
   class ModuleTest extends Specification {
       def "should test module functionality"() {
           // Test implementation
       }
   }
   ```

2. Add test application properties:
   ```yaml
   # src/test/resources/application-test.yml
   logging:
     level:
       com.bunny: DEBUG
   ```

3. Setup `log4j2.xml` for test logging
4. Create bootstrap configuration files for test context
5. Add test dependencies to module `pom.xml`

## Testing Patterns

### Test Structure
- Tests are primarily written in **Groovy** using Spock framework
- Test files follow the pattern `*Test.groovy`
- Use `@SpringBootTest` annotation for integration tests
- Separate test configuration in `application-test.yml`

### Test Configuration
```yaml
# Standard test configuration
spring:
  profiles:
    active: test
logging:
  level:
    root: INFO
    com.bunny: DEBUG
```

## Commands

| Command | Purpose |
|---------|---------|
| `/fix-initial` | Apply quick fixes after initial implementation |
| `/new-service-module` | Create a complete new service module with auto-configuration |
| `/setup-ci` | Setup or update GitHub Actions CI/CD pipeline |
| `/update-config` | Update project-wide configuration and dependencies |
| `/setup-tests` | Add comprehensive test infrastructure for modules |