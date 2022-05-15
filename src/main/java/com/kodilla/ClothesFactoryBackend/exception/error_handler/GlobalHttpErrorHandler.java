package com.kodilla.ClothesFactoryBackend.exception.error_handler;

import com.kodilla.ClothesFactoryBackend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Object> handleCartNotFoundException() {
        return new ResponseEntity<>("Cart with given id doesn't exist or can't be found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException() {
        return new ResponseEntity<>("User with given id doesn't exist or can't be found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<Object> handleUserEmailNotFoundException() {
        return new ResponseEntity<>("User with given email does not exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException() {
        return new ResponseEntity<>("Order with given id doesn't exist or can't be found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClothNotFoundException.class)
    public ResponseEntity<Object> handleClothNotFoundException() {
        return new ResponseEntity<>("Cloth with given id doesn't exist or can't be found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException() {
        return new ResponseEntity<>("Given email is already setup for an account.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Object> handleWrongPasswordException() {
        return new ResponseEntity<>("Wrong password.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotPaidException.class)
    public ResponseEntity<Object> handleOrderNotPaidException() {
        return new ResponseEntity<>("Order has not been paid yet.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(OrderAlreadyPaidException.class)
    public ResponseEntity<Object> handleOrderAlreadyPaidException() {
        return new ResponseEntity<>("Order has already been paid.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(OrderAlreadySentException.class)
    public ResponseEntity<Object> handleOrderAlreadySentException() {
        return new ResponseEntity<>("Order has already been sent.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<Object> handleEmptyCartException() {
        return new ResponseEntity<>("You cannot create an order from an empty cart.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(CurrencyExchangeFailedException.class)
    public ResponseEntity<Object> handleCurrencyExchangeFailedException() {
        return new ResponseEntity<>("Currency exchange failed, try again later.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfanityCheckFailedException.class)
    public ResponseEntity<Object> handleProfanityCheckFailedException() {
        return new ResponseEntity<>("Profanity check failed, try again later.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClothPrintContainsBadWordsException.class)
    public ResponseEntity<Object> handleClothPrintContainsBadWordsException() {
        return new ResponseEntity<>("Cloth print cannot contain any bad words, please change your choice.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(QuoteNotFoundException.class)
    public ResponseEntity<Object> handleQuoteNotFoundException() {
        return new ResponseEntity<>("Quote could not be found, please try again later.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailVerificationFailedException.class)
    public ResponseEntity<Object> handleEmailCheckFailedException() {
        return new ResponseEntity<>("Email verification failed, please try again later.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAddressDoesNotExistException.class)
    public ResponseEntity<Object> handleEmailAddressDoesNotExistException() {
        return new ResponseEntity<>("This email address does not exist, please provide a different one.", HttpStatus.BAD_REQUEST);
    }
}
