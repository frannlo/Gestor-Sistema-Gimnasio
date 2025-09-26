package com.gimnasio.reservas.exception;

public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
}
