package com.example.springmadness.service;

import com.example.springmadness.graphql.input.NameSearch;
import com.example.springmadness.helper.NameHelper;
import com.example.springmadness.model.Name;
import com.example.springmadness.repo.NameRepo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Service
public class NameService {

    private final NameRepo nameRepo;

    @Autowired
    public NameService(NameRepo nameRepo) {
        this.nameRepo = nameRepo;
    }

    public Name updateName(long nameId, @NotNull Name name) {
        final Name existingName = findExistingName(nameId, name);
        if (NameHelper.mergeName(existingName, name)) {
            nameRepo.save(existingName);
        }
        return existingName;
    }

    private Name findExistingName(long nameId, Name name) {
        final Optional<Name> existingName = nameRepo.findById(nameId);
        if (existingName.isEmpty()) {
            throw new NoSuchElementException(String.format("Can not update Name, id '%s' not found.", nameId));
        }
        return existingName.get();
    }

    public List<Name> getNames(NameSearch input) {
        ExampleMatcher matcher = input.findAny() ? ExampleMatcher.matchingAny() : ExampleMatcher.matchingAll();
        matcher = matcher
                .withIgnoreNullValues()
                .withMatcher("firstName", contains().ignoreCase())
                .withMatcher("lastName", contains().ignoreCase());
        final Name name = input.getName();
        final Example<Name> example = Example.of(name, matcher);
        return nameRepo.findAll(example);
    }
}
