package com.example.springmadness.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class MadError {

    private @NotNull
    final HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private @NotNull LocalDateTime timestamp;
    private @NotNull
    final String message;
    private @NotNull
    final String debugMessage;

    MadError() {
        this(HttpStatus.OK);
    }

    MadError(HttpStatus status) {
        this(status, null, null);
    }

    MadError(HttpStatus status, Throwable ex) {
        this(status, "Unexpected error: ", ex);
    }

    MadError(HttpStatus status, String message) {
        this(status, message, null, LocalDateTime.now());
    }

    MadError(HttpStatus status, String message, Throwable ex) {
        this(status, message, ex, LocalDateTime.now());
    }

    private MadError(HttpStatus status, String message, Throwable ex, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.debugMessage = null != ex ? ex.getLocalizedMessage() : "No exception captured";
    }

    @JsonProperty("statusValue")
    public int getStatusValue() {
        return status.value();
    }
}
