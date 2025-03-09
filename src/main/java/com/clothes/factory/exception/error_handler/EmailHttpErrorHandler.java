package com.clothes.factory.exception.error_handler;

import com.clothes.factory.exception.email.EmailAddressDoesNotExistException;
import com.clothes.factory.exception.email.EmailVerificationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmailHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailVerificationFailedException.class)
    public ResponseEntity<Object> handleEmailCheckFailedException() {
        return new ResponseEntity<>("Email verification failed, please try again later.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAddressDoesNotExistException.class)
    public ResponseEntity<Object> handleEmailAddressDoesNotExistException() {
        return new ResponseEntity<>("This email address does not exist, please provide a different one.", HttpStatus.BAD_REQUEST);
    }

}
