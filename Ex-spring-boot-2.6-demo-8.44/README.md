## Drools integration with Spring Boot

- Spring Boot 2.6.6
- Drools 8.44.0.Final
- Java 11
- Using `getKieClasspathContainer()` method to load the rules from the classpath. So you include a kjar in your appliation.
    - If you use `newKieContainer(releaseId)`, you can load the kjar from a maven repository.