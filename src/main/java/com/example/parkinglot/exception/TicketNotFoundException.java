package com.example.parkinglot.exception;

import com.example.parkinglot.model.Ticket;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(Long ticketId){
        super("No ticket for " + ticketId);
    }
}
