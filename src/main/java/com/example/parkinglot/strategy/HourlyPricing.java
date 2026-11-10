package com.example.parkinglot.strategy;

import com.example.parkinglot.model.VehicleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HourlyPricing implements PricingStrategy {

    @Override
    public BigDecimal calculateFee(VehicleType vehicleType, LocalDateTime entry, LocalDateTime exit){
       long minutes = Duration.between(entry, exit).toMinutes();

       long hours = Math.max(1, (minutes+59)/60);

       BigDecimal rate =
               vehicleType == VehicleType.CAR
               ? BigDecimal.valueOf(50)
                       :BigDecimal.valueOf(20);

       return rate.multiply(BigDecimal.valueOf(hours));

    }

}
