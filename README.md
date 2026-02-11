
# Warehouse Metrics Dashboard

This is a Spring Boot application used internally to monitor several operational metrics in a warehouse environment.  
The dashboard connects to multiple databases and aggregates data such as fill levels, WIP, lane status, transporter/lift state, and pick counts.  
It is displayed continuously on a screen inside the warehouse to give supervisors quick visibility into operational performance.

## Features
- Connects to four separate databases  
- Displays metrics such as:  
  - LDD count  
  - Area fill levels  
  - Release problems  
  - WIP  
  - P2P picks  
  - Buffer picks  
  - PB lane status  
  - Transporter status  
  - Lift status  
  - DBH lanes  
- Uses raw SQL through JdbcTemplate  
- Spring MVC and Thymeleaf for the UI  
- Designed for continuous display

## Technologies
- Java 17  
- Spring Boot 3  
- Spring MVC  
- JdbcTemplate  
- Thymeleaf  
- JUnit 5  
- Mockito  
- Maven

## Project Structure
- **controller** – handles HTTP requests and prepares data for the view  
- **service** – contains business logic and communicates with repositories  
- **repository** – SQL queries executed with JdbcTemplate  
- **dto** – simple classes to map query results  
- **config** – configuration for four data sources and JdbcTemplates  

## Tests

### Service Layer Tests
Service tests use Mockito to mock the repository.  
They verify:

- returned values  
- interaction with repository methods  
- handling of empty and non-empty lists  
Example:

```java
@Test
void getLDDs_returnsItemFromRepository() {
    Mockito.when(dashboardRepository.getLDDs()).thenReturn("101");
    String result = dashboardService.getLDDs();
    assertEquals("101", result);
    Mockito.verify(dashboardRepository).getLDDs();
}
```
### Controller Tests
Controller tests use MockMvc to verify:
- /dashboard returns HTTP 200 and correct view
- invalid endpoint returns HTTP 404

## Purpose
The application is used in a warehouse environment to combine data from several internal systems into one dashboard.
It helps with:
- monitoring daily operations
- detecting issues early
- reducing manual checks
- improving overall visiblity for supervisors

## License
Internal operational tool, published publicly in anonymized form for portfolio purposes.
