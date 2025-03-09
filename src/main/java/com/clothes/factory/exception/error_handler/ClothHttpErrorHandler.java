package com.clothes.factory.exception.error_handler;

import com.clothes.factory.exception.cloth.ClothNotFoundException;
import com.clothes.factory.exception.cloth.ClothPrintContainsBadWordsException;
import com.clothes.factory.exception.cloth.ClothWithQuantityZeroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ClothHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClothNotFoundException.class)
    public ResponseEntity<Object> handleClothNotFoundException() {
        return new ResponseEntity<>("Cloth with given id doesn't exist or can't be found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClothWithQuantityZeroException.class)
    public ResponseEntity<Object> handleClothWithQuantityZeroException() {
        return new ResponseEntity<>("Quantity cannot be 0.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClothPrintContainsBadWordsException.class)
    public ResponseEntity<Object> handleClothPrintContainsBadWordsException() {
        return new ResponseEntity<>("Cloth print cannot contain any bad words, please change your choice.", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
