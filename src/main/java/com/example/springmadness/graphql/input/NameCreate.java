package com.example.springmadness.graphql.input;

import com.example.springmadness.model.Name;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class NameCreate {
    private String firstName;
    private String lastName;

    @JsonIgnore
    public Name getName() {
        return new Name()
                .setFirstName(firstName)
                .setLastName(lastName);
    }
}
