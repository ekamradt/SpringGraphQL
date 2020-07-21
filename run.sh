
rm -f ./target/SpringGraphQL-0.0.1-SNAPSHOT.jar
mvn clean package spring-boot:repackage
java -jar ./target/SpringGraphQL-0.0.1-SNAPSHOT.jar
