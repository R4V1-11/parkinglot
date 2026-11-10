package com.example.parkinglot.strategy;

import com.example.parkinglot.model.ParkingSpot;
import com.example.parkinglot.model.VehicleType;
import com.example.parkinglot.repository.ParkingSpotRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class LowestFloorFirstAllocation implements SpotAllocationStrategy{
    private final ParkingSpotRepository spotRepository;

    public LowestFloorFirstAllocation(ParkingSpotRepository spotRepository){
        this.spotRepository = spotRepository;
    }

    @Override
    public Optional<ParkingSpot> findSpot(VehicleType type){
        List<ParkingSpot> freeSpots = spotRepository.findFreeSpots(type);

        return freeSpots.stream()
                .min(Comparator.
                        comparing((ParkingSpot s) ->
                                s.getFloor().getFloorNumber())
                        .thenComparing(ParkingSpot::getSpotNumber));

    }

}
