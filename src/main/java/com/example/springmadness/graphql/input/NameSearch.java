package com.example.springmadness.graphql.input;

import com.example.springmadness.model.Name;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class NameSearch {
    private String firstName;
    private String lastName;
    private String anyName;

    @JsonIgnore
    public Name getName() {
        return new Name()
                .setFirstName(firstName != null ? firstName : anyName)
                .setLastName(lastName != null ? lastName : anyName);
    }

    @JsonIgnore
    public boolean findAny() {
        return anyName != null && (firstName == null || lastName == null);
    }
}
