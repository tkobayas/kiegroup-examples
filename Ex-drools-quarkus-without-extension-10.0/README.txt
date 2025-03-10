This is a drools + quarkus example using KieFileSystem.

Note that this example doesn't depend on drools-quarkus extension, because this builds rules at runtime.

## dev mode

./mvnw quarkus:dev

curl http://localhost:8080/test/rule

native build is not supported.
