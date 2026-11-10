package com.example.parkinglot.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyParkedException.class)
    public ProblemDetail alreadyParked(
            AlreadyParkedException ex
    ){
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ParkingFullException.class)
    public ProblemDetail full(
            ParkingFullException ex
    ){
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.SERVICE_UNAVAILABLE,
                ex.getMessage()
        );
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ProblemDetail notFound(
            TicketNotFoundException ex
    ) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage()
        );
    }

    @ExceptionHandler(IllegalAccessError.class)
    public ProblemDetail closed(
            IllegalStateException ex
    ){
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, ex.getMessage()
        );
    }

    @ExceptionHandler(TicketAlreadyClosedException.class)
    public ProblemDetail alreadyClosed(
            TicketAlreadyClosedException ex
    ){
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, ex.getMessage()
        );
    }
}
