package com.example.springmadness.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

@Slf4j
public class ControllerHelper {
    public static <T> ResponseEntity<MadResponse<T>> runAndReturn(Supplier<T> supplier, String msg) {
        try {
            return MadResponse.buildOk(supplier.get());
        } catch (Exception e) {
            log.error("Error:", e);
            return MadResponse.internalError();
        }
    }
}
