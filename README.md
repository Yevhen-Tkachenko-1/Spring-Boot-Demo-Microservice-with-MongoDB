# Spring-Boot-Demo-Microservice-with-MongoDB

Learn and play with Spring Framework using Spring Boot and MongoDB

Topics covered:
- Spring CommandLineRunner
- Spring Data (Implicit API Export, Search, Sorting, Paging)
- Spring MVC (REST Controllers)
- Spring with NoSQL databases
- Spring and Custom params validation (for nulls, consistency, value bounds)

Implemented based on LinkedIn learning course:
[Creating Your First Spring Boot Microservice](https://www.linkedin.com/learning/creating-your-first-spring-boot-microservice)

### Tech stack

- Java 13
- Maven
- Spring Boot 2.2.0.RELEASE (data-mongodb, data-rest, web, test)
- Jakarta (validation)
- MongoDB (embedded)
- Lombok

### Local run

1. Run `main()` method in [application](src/main/java/com/yevhent/microservicedemo/SpringBootMicroservice.java) class. 
   Spring app and embedded MongoDB should be started.
1. Check output in console for populating embedded DB with init data.
1. Use this [Postman collection](postman/requests.json) to make REST API calls.