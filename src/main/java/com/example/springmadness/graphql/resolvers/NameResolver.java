package com.example.springmadness.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.springmadness.graphql.input.NameCreate;
import com.example.springmadness.graphql.input.NameSearch;
import com.example.springmadness.graphql.input.NameUpdate;
import com.example.springmadness.model.Name;
import com.example.springmadness.repo.NameRepo;
import com.example.springmadness.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NameResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final NameService nameService;
    private final NameRepo nameRepo;

    @Autowired
    public NameResolver(NameService nameService, NameRepo nameRepo) {
        this.nameService = nameService;
        this.nameRepo = nameRepo;
    }

    public List<Name> getNames(NameSearch input) {
        return nameService.getNames(input);
    }

    public Name createName(NameCreate nameCreate) {
        return nameRepo.save(nameCreate.getName());
    }

    public Name updateName(NameUpdate nameUpdate) {
        return nameService.updateName(nameUpdate.getNameId(), nameUpdate.getName());
    }
}
