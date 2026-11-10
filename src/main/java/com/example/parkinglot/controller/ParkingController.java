package com.example.parkinglot.controller;

import com.example.parkinglot.model.Ticket;
import com.example.parkinglot.model.VehicleType;
import com.example.parkinglot.service.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/parking")
public class ParkingController {

    private final ParkingService service;
    public ParkingController(ParkingService s) {
        this.service = s;
    }

    @PostMapping("/park")
    public TicketResponse park(
            @RequestParam String vehicleNumber,
            @RequestParam VehicleType type) {
        Ticket t = service.park(vehicleNumber, type);

        return new TicketResponse(t.getId(),
                t.getSpot().getFloor().getFloorNumber(),
                t.getSpot().getSpotNumber(),
                t.getEntryTime());
    }

    @PostMapping("/unpark/{ticketId}")
    public ReceiptResponse unpark(
            @PathVariable Long ticketId) {
        Ticket t = service.unpark(ticketId);
        return new ReceiptResponse(t.getId(),
                t.getVehicleNumber(),
                t.getEntryTime(),
                t.getExitTime(),
                t.getFee());
    }
}

record TicketResponse(Long ticketId,
                      int floor, int spot,
                      LocalDateTime entryTime) {}

record ReceiptResponse(Long ticketId,
                       String vehicleNumber,
                       LocalDateTime entryTime,
                       LocalDateTime exitTime,
                       BigDecimal fee) {}