package fr.diginamic.hello.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VilleControllerAdvice {

    @ExceptionHandler(VilleException.class)
    public ResponseEntity<String> traiterException(VilleException ve){

        return ResponseEntity.badRequest().body(ve.getMessage());

    }

}
