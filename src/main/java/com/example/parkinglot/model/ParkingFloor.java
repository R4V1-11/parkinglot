package com.example.parkinglot.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="parking_floors")
public class ParkingFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique = true)
    private int floorNumber;

    @OneToMany(mappedBy = "floor")
    private List<ParkingSpot> spots = new ArrayList<>();

    protected ParkingFloor() {

    }

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Long getId() {return id;}
    public int getFloorNumber() { return floorNumber; }
    public List<ParkingSpot> getSpots() {return spots; }

}
