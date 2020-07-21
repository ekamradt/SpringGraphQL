package com.example.springmadness.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
public class MadResponse<T> {

    private static final int httpStatusOk = HttpStatus.OK.value();

    private MadError apiError;
    private T entity;
    private Collection<T> entities;

    private MadResponse() {
    }

    @JsonProperty("status")
    public int getStatus() {
        return apiError != null ? apiError.getStatus().value() : httpStatusOk;
    }

    public static <T> MadResponse<T> body(T entity) {
        final MadResponse<T> res = new MadResponse<>();
        res.entity = entity;
        return res;
    }

    public static <T> MadResponse<T> body(Collection<T> entities) {
        final MadResponse<T> res = new MadResponse<>();
        res.entities = entities;
        return res;
    }

    public static <T> MadResponse<T> body(T entity, MadError apiError) {
        final MadResponse<T> res = new MadResponse<>();
        res.entity = entity;
        res.apiError = apiError;
        return res;
    }

    public static <T> MadResponse<T> body(MadError apiError) {
        final MadResponse<T> res = new MadResponse<>();
        res.apiError = apiError;
        return res;
    }

    public static <T> ResponseEntity<MadResponse<T>> buildOk(T entity) {
        return ResponseEntity.ok().body(MadResponse.body(entity));
    }
//
//    public static <T> ResponseEntity<MadResponse<T>> buildOk(Collection<T> entities) {
//        return ResponseEntity.ok().body(MadResponse.body(entities));
//    }
//
//    public static <T> ResponseEntity<MadResponse<T>> buildOk(Iterator<T> entities) {
//        final List<T> entityList = StreamSupport.stream(
//                Spliterators.spliteratorUnknownSize(entities, Spliterator.ORDERED)
//                , false)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok().body(MadResponse.body(entityList));
//    }

    public static <T> ResponseEntity<MadResponse<T>> internalError() {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(MadResponse.body(new MadError(status)));
    }

    public static <T> ResponseEntity<MadResponse<T>> internalError(Exception e) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(MadResponse.body(new MadError(status, e)));
    }

    public static <T> ResponseEntity<MadResponse<T>> internalError(String msg) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(MadResponse.body(new MadError(status, msg)));
    }

    public static <T> ResponseEntity<MadResponse<T>> conflictError(String message, Exception e) {
        final HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(MadResponse.body(new MadError(status, message, e)));
    }

    public static <T> ResponseEntity<MadResponse<T>> notFoundError() {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(MadResponse.body(new MadError(status)));
    }
}
