package com.natixis.todoapp.adapters.apiweb.restapi;

import com.natixis.todoapp.adapters.apiweb.dto.ApiError;
import com.natixis.todoapp.domain.exception.BadRequest;
import com.natixis.todoapp.domain.exception.InvalidFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class TaskExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ApiError handleException(Exception exception) {
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ApiError(errors);
    }

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ApiError handleBadRequest(BadRequest exception) {
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ApiError(errors);
    }

    @ExceptionHandler(InvalidFilter.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ApiError handleInvalidFilter(InvalidFilter exception) {
        List<String> errors = Collections.singletonList(exception.getMessage());
        return new ApiError(errors);
    }
}
