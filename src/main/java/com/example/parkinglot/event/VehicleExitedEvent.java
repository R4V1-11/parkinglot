package com.example.parkinglot.event;

import com.example.parkinglot.model.VehicleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VehicleExitedEvent(
        Long ticketId,
        String vehicleNumber,
        VehicleType vehicleType,
        LocalDateTime entryTime,
        LocalDateTime exitTime,
        BigDecimal fee
) {}