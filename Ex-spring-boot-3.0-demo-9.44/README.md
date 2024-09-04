## Drools integration with Spring Boot

- Spring Boot 3.0.5
- Drools 9.44.0.Final
- Java 17
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
### Notes

If you use Spring Boot 3.2+, you will hit the issue: https://github.com/apache/incubator-kie-drools/issues/5745

Please wait for a fix.