package com.little3snake.subscriptiontracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private List<String> details;
}