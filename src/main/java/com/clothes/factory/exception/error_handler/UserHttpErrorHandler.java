package com.clothes.factory.exception.error_handler;

import com.clothes.factory.exception.user.UserAlreadyExistsException;
import com.clothes.factory.exception.user.UserEmailNotFoundException;
import com.clothes.factory.exception.user.UserNotFoundException;
import com.clothes.factory.exception.user.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException() {
        return new ResponseEntity<>("User with given id doesn't exist or can't be found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<Object> handleUserEmailNotFoundException() {
        return new ResponseEntity<>("User with given email does not exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException() {
        return new ResponseEntity<>("Given email is already setup for an account.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Object> handleWrongPasswordException() {
        return new ResponseEntity<>("Wrong password.", HttpStatus.BAD_REQUEST);
    }

}
