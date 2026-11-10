package com.example.parkinglot.config;

import com.example.parkinglot.model.ParkingFloor;
import com.example.parkinglot.model.ParkingSpot;
import com.example.parkinglot.model.VehicleType;
import com.example.parkinglot.repository.ParkingFloorRepository;
import com.example.parkinglot.repository.ParkingSpotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder
        implements CommandLineRunner {

    private final ParkingFloorRepository floors;
    private final ParkingSpotRepository spots;

    public DataSeeder(ParkingFloorRepository f,
                      ParkingSpotRepository s) {
        this.floors = f;
        this.spots = s;
    }

    @Override
    public void run(String... args) {
        if (floors.count() > 0) return;
        for (int f = 0; f < 2; f++) {
            ParkingFloor floor =
                    floors.save(new ParkingFloor(f));
            spots.save(new ParkingSpot(1,
                    VehicleType.CAR, floor));
            spots.save(new ParkingSpot(2,
                    VehicleType.CAR, floor));
            spots.save(new ParkingSpot(3,
                    VehicleType.BIKE, floor));
        }
    }
}
