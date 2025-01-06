with drools 10.0.0,

```shell
mvn clean install
```
fails with
```
[ERROR] Unable to build KieBaseModel:defaultKieBase
MissingDependencyError: You're trying to use the Truth Maintenance System without having imported it. Please add the module org.drools:drools-tms to your classpath.
```

Workaround:
```shell
mvn clean install -Ddrools.parallelRulesBuildThreshold=-1
```
