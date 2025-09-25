package com.gimnasio.reservas.exception;

public class ClienteExistenteException extends RuntimeException{
    public ClienteExistenteException(String message) {
        super(message);
    }
}
