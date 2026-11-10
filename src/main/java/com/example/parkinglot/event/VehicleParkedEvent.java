package com.example.parkinglot.event;

import com.example.parkinglot.model.VehicleType;

import java.time.LocalDateTime;

public record VehicleParkedEvent(
        Long ticketId,
        String vehicleNumber,
        VehicleType vehicleType,
        int floorNumber,
        int spotNumber,
        LocalDateTime entryTime
) {}
