package com.clothes.factory.exception.error_handler;

import com.clothes.factory.exception.cart.CartNotFoundException;
import com.clothes.factory.exception.cart.EmptyCartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CartHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Object> handleCartNotFoundException() {
        return new ResponseEntity<>("Cart with given id doesn't exist or can't be found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<Object> handleEmptyCartException() {
        return new ResponseEntity<>("You cannot create an order from an empty cart.", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
