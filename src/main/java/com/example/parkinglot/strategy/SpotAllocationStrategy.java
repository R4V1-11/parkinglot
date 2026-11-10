package com.example.parkinglot.strategy;

import com.example.parkinglot.model.ParkingSpot;
import com.example.parkinglot.model.VehicleType;

import java.util.Optional;

public interface SpotAllocationStrategy {
    Optional<ParkingSpot> findSpot(VehicleType type);
}
