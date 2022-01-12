package service;

import lombok.Getter;

public class ServiceError extends RuntimeException{

    @Getter
    private final String message;

    public ServiceError(int code, String message){
        this.message = message + "." + code;
    }
}
