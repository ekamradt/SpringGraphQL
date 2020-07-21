package com.example.springmadness.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Accessors(chain = true)
public class Name {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nameId;

    private String firstName;
    private String lastName;

    public Name() {
    }
}
