package com.clothes.factory.exception.error_handler;

import com.clothes.factory.exception.order.OrderAlreadyPaidException;
import com.clothes.factory.exception.order.OrderAlreadySentException;
import com.clothes.factory.exception.order.OrderNotFoundException;
import com.clothes.factory.exception.order.OrderNotPaidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class OrderHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException() {
        log.error("Order not found.");
        return new ResponseEntity<>("Order with given id doesn't exist or can't be found.", HttpStatus.NOT_FOUND);
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

}
