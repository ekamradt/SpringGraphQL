# SpringGraphQL

Spring Madness Sample Application

#### Stack
* Java 11
* Python 3 for a little integration test
  * src/main/python/mad_test.py
* Lombok
* Spring Boot
  * src/main/java/com/example/springmadness/Application.java
* Spring Data/JPA
  * src/main/java/com/example/springmadness/repo/NameRepo.java  
  * src/main/java/com/example/springmadness/service/NameService.java  
* Spring Rest
  * src/main/java/com/example/springmadness/controller/NameController.java
* GraphQL / GraphiQL
  * src/main/java/com/example/springmadness/graphql/resolvers/NameResolver.java
* H2 in-memory database
* LiquiBase (for coordinating SQL files)

#### Run Application

* See ./run.sh

```
rm -f ./target/SpringGraphQL-0.0.1-SNAPSHOT.jar
mvn clean package spring-boot:repackage
java -jar ./target/SpringGraphQL-0.0.1-SNAPSHOT.jar
```
  
#### Rest Endpoints (APIs)

* See src/main/python/mad_test.py

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| GET | .../api/v1/names | List all names |
| POST | .../api/v1/name (w/ json data) | Create a name |
| PUT | .../api/v1/name (w/ json data) | Update a name |
| DELETE | .../api/v1/name/\<nameId> | Delete a name |

#### GraphQL Examples


Query
```
{
  names(input: {firstName:"t"}) {
    nameId
    firstName
    lastName
  }
}
```

Results: Query
```
{
  "data": {
    "names": [
      {
        "nameId": "2",
        "firstName": "Betty",
        "lastName": "Tables"
      }
    ]
  }
}
```

Mutation Create
```
mutation {
  createName(input: {
    firstName: "Fred"
    lastName: "Johnson"
  }){
    nameId
    firstName
    lastName
  }
}
```

Results: Mutation Create
```
{
  "data": {
    "createName": {
      "nameId": "3",
      "firstName": "Fred",
      "lastName": "Johnson"
    }
  }
}
```

Mutation Update
```
mutation {
  updateName(input: {
    nameId: "3"
    firstName: "Fred17"
    lastName: "Johnson17"
  }){
    nameId
    firstName
    lastName
  }
}
```

Results: Mutation Update
```
{
  "data": {
    "updateName": {
      "nameId": "3",
      "firstName": "Fred17",
      "lastName": "Johnson17"
    }
  }
}
```

