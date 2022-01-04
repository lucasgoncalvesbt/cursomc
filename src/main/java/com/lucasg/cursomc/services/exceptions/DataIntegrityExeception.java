package com.lucasg.cursomc.services.exceptions;

public class DataIntegrityExeception extends RuntimeException {
    public DataIntegrityExeception(String msg) {
        super(msg);
    }

    public DataIntegrityExeception(String msg, Throwable cause) {
        super(msg, cause);
    }
}
