# SpringGraphQL

Sample app 

#### Stack
* Java 11
* Lombok
* Spring Boot
* Spring Data/JPA
* Spring Rest
* GraphQL / GraphiQL
* LiquiBase (for coordinating SQL files)

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

