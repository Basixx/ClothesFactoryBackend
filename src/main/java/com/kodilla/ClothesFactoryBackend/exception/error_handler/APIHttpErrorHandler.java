package com.kodilla.ClothesFactoryBackend.exception.error_handler;

import com.kodilla.ClothesFactoryBackend.exception.api.CurrencyExchangeFailedException;
import com.kodilla.ClothesFactoryBackend.exception.api.ProfanityCheckFailedException;
import com.kodilla.ClothesFactoryBackend.exception.api.QuoteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CurrencyExchangeFailedException.class)
    public ResponseEntity<Object> handleCurrencyExchangeFailedException() {
        return new ResponseEntity<>("Currency exchange failed, try again later.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfanityCheckFailedException.class)
    public ResponseEntity<Object> handleProfanityCheckFailedException() {
        return new ResponseEntity<>("Profanity check failed, try again later.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuoteNotFoundException.class)
    public ResponseEntity<Object> handleQuoteNotFoundException() {
        return new ResponseEntity<>("Quote could not be found, please try again later.", HttpStatus.NOT_FOUND);
    }

}
