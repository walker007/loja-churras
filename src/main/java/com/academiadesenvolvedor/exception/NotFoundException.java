package com.academiadesenvolvedor.exception;

public class NotFoundException extends RuntimeException{
    private int codeStatus = 404;
    public NotFoundException(String message){
        super(message);
    }
}
