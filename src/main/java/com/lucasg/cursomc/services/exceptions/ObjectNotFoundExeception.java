package com.lucasg.cursomc.services.exceptions;

public class ObjectNotFoundExeception extends RuntimeException {
    public ObjectNotFoundExeception(String msg) {
        super(msg);
    }

    public ObjectNotFoundExeception(String msg, Throwable cause) {
        super(msg, cause);
    }
}
