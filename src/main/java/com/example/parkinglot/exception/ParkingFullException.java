package com.example.parkinglot.exception;

import com.example.parkinglot.model.VehicleType;

public class ParkingFullException extends RuntimeException{
    public ParkingFullException(VehicleType t) {
        super("No free spot for " + t);
    }
}
