package com.example.springmadness.graphql.input;

import com.example.springmadness.model.Name;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class NameUpdate {
    private Long nameId;
    private String firstName;
    private String lastName;

    @JsonIgnore
    public Name getName() {
        return new Name()
                .setNameId(nameId)
                .setFirstName(firstName)
                .setLastName(lastName);
    }
}
