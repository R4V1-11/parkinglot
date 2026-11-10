package com.example.parkinglot.strategy;

import com.example.parkinglot.model.VehicleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PricingStrategy {
    BigDecimal calculateFee(VehicleType vehicleType, LocalDateTime entry, LocalDateTime exit);
}
