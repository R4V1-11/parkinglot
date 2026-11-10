package com.example.parkinglot.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private BigDecimal fee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id", nullable = false)
    private ParkingSpot spot;

    protected Ticket() {

    }

    public Ticket(String vehicleNumber, VehicleType vehicleType, ParkingSpot spot){
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
    }

    public void close(LocalDateTime exit, BigDecimal fee){
        this.exitTime = exit;
        this.fee = fee;
    }

    public boolean isActive(){
        return exitTime == null;
    }

    public Long getId() {return id;}
    public String getVehicleNumber() {return vehicleNumber; }
    public VehicleType getVehicleType() { return vehicleType; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public ParkingSpot getSpot() { return spot; }
    public BigDecimal getFee(){ return fee;}
}
