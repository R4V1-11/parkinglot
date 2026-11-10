package com.example.parkinglot.listener;

import com.example.parkinglot.config.KafkaConfig;
import com.example.parkinglot.event.VehicleExitedEvent;
import com.example.parkinglot.event.VehicleParkedEvent;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

@Component
public class ParkingEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(
                    ParkingEventListener.class
            );


    @KafkaListener(topics = KafkaConfig.PARKED)
    public void onParked(VehicleParkedEvent e) {
        log.info("Parked: {} at floor {} spot {}",
                e.vehicleNumber(),
                e.floorNumber(),
                e.spotNumber());
    }

    @KafkaListener(topics = KafkaConfig.EXITED)
    public void onExited(VehicleExitedEvent e){
        log.info("Exited: {} paid {}",e.vehicleNumber(),e.fee() );
    }
}
