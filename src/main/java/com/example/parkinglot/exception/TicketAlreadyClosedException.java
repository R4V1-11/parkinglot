package com.example.parkinglot.exception;

public class TicketAlreadyClosedException
        extends RuntimeException {
    public TicketAlreadyClosedException(Long id) {
        // note the spaces around the id
        super("Ticket " + id
                + " is already checked out");
    }
}
