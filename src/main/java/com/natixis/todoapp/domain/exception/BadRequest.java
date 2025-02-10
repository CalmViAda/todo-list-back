package com.natixis.todoapp.domain.exception;

public class BadRequest extends Exception {

    public BadRequest() {
        super("Request is not correct !");
    }
    public String getMessage() {
        return "Request is not correct !";
    }
}
