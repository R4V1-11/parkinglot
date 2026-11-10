package com.example.parkinglot.exception;

public class AlreadyParkedException extends RuntimeException{
    public AlreadyParkedException(String v) {
        super(v + " is already parked");
    }
}
