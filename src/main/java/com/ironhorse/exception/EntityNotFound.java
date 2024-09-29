package com.ironhorse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public abstract class EntityNotFound extends RuntimeException {

    public EntityNotFound(String message){
        super(message);
    }

}
