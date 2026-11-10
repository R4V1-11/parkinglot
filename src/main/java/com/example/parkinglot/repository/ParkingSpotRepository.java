package com.example.parkinglot.repository;

import com.example.parkinglot.model.ParkingSpot;
import com.example.parkinglot.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    @Query("""
    SELECT spot FROM ParkingSpot spot
    WHERE spot.occupied = false
      AND spot.spotType = :spotType
    ORDER BY spot.floor.floorNumber ASC
    """)
    List<ParkingSpot> findFreeSpots(VehicleType spotType);
}
