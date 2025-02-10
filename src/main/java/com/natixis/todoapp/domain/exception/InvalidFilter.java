package com.natixis.todoapp.domain.exception;

public class InvalidFilter extends Exception {

    public InvalidFilter(String filter) {
        super("Invalid filter: "+ filter);
    }
}