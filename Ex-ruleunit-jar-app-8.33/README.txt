mvn dependency:copy-dependencies -DoutputDirectory=lib

mvn clean install

java -jar target/basic-example-1.0.0-SNAPSHOT.jar
