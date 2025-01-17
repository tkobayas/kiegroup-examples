This is a drools-quarkus example using traditional KIE API instead of RuleUnit

## dev mode

./mvnw quarkus:dev

curl http://localhost:8080/test/rule

## native build

./mvnw package -Dnative -Dquarkus.native.container-build=true

./target/code-with-quarkus-1.0.0-SNAPSHOT-runner

curl http://localhost:8080/test/rule