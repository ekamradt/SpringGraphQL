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


#### Rest Endpoints (APIs)

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

