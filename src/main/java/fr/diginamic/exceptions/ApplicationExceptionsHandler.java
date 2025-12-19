package fr.diginamic.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionsHandler {

    @ExceptionHandler(VilleException.class)
    public ResponseEntity<String> traiterException(VilleException ve){

        return ResponseEntity.badRequest().body(ve.getMessage());

    }

    @ExceptionHandler(DepartementException.class)
    public ResponseEntity<String> traiterException(DepartementException de){

        return ResponseEntity.badRequest().body(de.getMessage());

    }

}
