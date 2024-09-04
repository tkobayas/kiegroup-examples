## Drools integration with Spring Boot

- Spring Boot 2.6.6
- Drools 8.44.0.Final
- Java 11
- Using `getKieClasspathContainer()` method to load the rules from the classpath. So you include a kjar in your application.
    - If you use `newKieContainer(releaseId)`, you can load the kjar from a maven repository.

### How to run

```shell
mvn clean install
java -jar demo/target/demo-0.0.1-SNAPSHOT.jar
```

In a new terminal,
```shell
curl http://localhost:8080/hello
```
