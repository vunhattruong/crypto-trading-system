package com.example.collector.domain.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Date   timestamp;
    private String message;
    private String details;
}
