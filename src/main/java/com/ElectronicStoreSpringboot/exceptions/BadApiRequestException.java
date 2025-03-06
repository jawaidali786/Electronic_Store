package com.ElectronicStoreSpringboot.exceptions;


public class BadApiRequestException extends RuntimeException{

    public BadApiRequestException() {
        super("Bad Request !!");
    }

    public BadApiRequestException(String messsage){
        super(messsage);
    }

}
