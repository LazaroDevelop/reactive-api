package net.developer.space.reactive_api.exception;

import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    private String message;

    public JwtAuthenticationException(){
        super("Invalid token");
    }

    public JwtAuthenticationException(String message){
        super(message);
    }
}
