## Drools 10.0.0 + rule flow bpmn + kjar

```shell
mvn clean install -DskipTests
mvn test
```

mvn builds a kjar with executable-model. See that the project has a dependency org.kie.kogito:jbpm-bpmn2 to parse bpmn.

KjarTest fetches the kjar and start the rule flow process.

DroolsTest main can also start the process, but with non-executable-model, so deprecated usage.