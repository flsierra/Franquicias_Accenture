package Accenture.Franquicias_Accenture.Application.Exceptions;


import Accenture.Franquicias_Accenture.Interfaces.ResponseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(RegistroApiException.class)
    public ResponseEntity<ResponseError> manejarRegistroApiException(RegistroApiException ex) {

        ResponseError res = new ResponseError(
                ex.getMessage(),
                ex.getCode().value()
        );

        return new ResponseEntity<>(res, ex.getCode());
    }
}
