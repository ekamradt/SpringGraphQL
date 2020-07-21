package com.example.springmadness.controller;

import com.example.springmadness.model.Name;
import com.example.springmadness.repo.NameRepo;
import com.example.springmadness.service.NameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.springmadness.controller.ControllerHelper.runAndReturn;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class NameController {

    private final NameService nameService;
    private final NameRepo nameRepo;

    @Autowired
    public NameController(NameService nameService, NameRepo nameRepo) {
        this.nameService = nameService;
        this.nameRepo = nameRepo;
    }

    @GetMapping("/names")
    public ResponseEntity<MadResponse<List<Name>>> getNames() {
        //return runAndReturn(nameRepo::findAll, "Problem with Name List.");
        return runAndReturn(this::getAllNames, "Problem with Name List.");
    }

    private List<Name> getAllNames() {
        final Iterable<Name> entities = nameRepo.findAll();
        final List<Name> entityList = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(entities.iterator(), Spliterator.ORDERED), false)
                .collect(Collectors.toList());
        return entityList;
    }


    @PostMapping("/name")
    public ResponseEntity<MadResponse<Name>> createName(@RequestBody Name name) {
        return runAndReturn(() -> nameRepo.save(name), "Problem saving Name");
    }

    @PutMapping("/name/{id}")
    public ResponseEntity<MadResponse<Name>> updateName(@PathVariable("id") Long nameId, @RequestBody Name name) {
        return runAndReturn(() -> nameService.updateName(nameId, name), "Problem updating Name");
    }

    @GetMapping("/name/{id}")
    public ResponseEntity<MadResponse<Name>> getName(@PathVariable("id") Long nameId) {
        try {
            final Optional<Name> name = nameRepo.findById(nameId);
            return name
                    .map(MadResponse::buildOk)
                    .orElseGet(MadResponse::notFoundError);
        } catch (Exception e) {
            log.error("Problem getting Cell Phone", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
