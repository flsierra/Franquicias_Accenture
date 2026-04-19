package Accenture.Franquicias_Accenture.Application.Exceptions;

import org.springframework.http.HttpStatus;

public class RegistroApiException extends RuntimeException {

    private HttpStatus code;

    public RegistroApiException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }
}