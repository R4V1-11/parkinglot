package com.example.parkinglot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_spots")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int spotNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType spotType;

    @Column(nullable = false)
    private boolean occupied = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", nullable = false)
    private ParkingFloor floor;

    @Version
    private Long version;

    protected ParkingSpot() {
    }

    public ParkingSpot(int spotNumber, VehicleType spotType, ParkingFloor floor) {
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.floor = floor;
    }

    public void occupy() {
        if (occupied) {
            throw new IllegalStateException("Spot already occupied");
        }
        this.occupied = true;
    }

    public void free() {
        this.occupied = false;
    }

    public Long getId() { return id; }
    public int getSpotNumber() { return spotNumber; }
    public VehicleType getSpotType() { return spotType; }
    public boolean isOccupied() { return occupied; }
    public ParkingFloor getFloor() { return floor; }
}