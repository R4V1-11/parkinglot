package com.example.parkinglot.service;

import com.example.parkinglot.config.KafkaConfig;
import com.example.parkinglot.event.VehicleExitedEvent;
import com.example.parkinglot.event.VehicleParkedEvent;
import com.example.parkinglot.exception.AlreadyParkedException;
import com.example.parkinglot.exception.ParkingFullException;
import com.example.parkinglot.exception.TicketAlreadyClosedException;
import com.example.parkinglot.exception.TicketNotFoundException;
import com.example.parkinglot.model.ParkingSpot;
import com.example.parkinglot.model.Ticket;
import com.example.parkinglot.model.VehicleType;
import com.example.parkinglot.repository.ParkingSpotRepository;
import com.example.parkinglot.repository.TicketRepository;
import com.example.parkinglot.strategy.HourlyPricing;
import com.example.parkinglot.strategy.PricingStrategy;
import com.example.parkinglot.strategy.SpotAllocationStrategy;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ParkingService {

    private final SpotAllocationStrategy allocation;

    private final PricingStrategy pricing;

    private final ParkingSpotRepository spotRepo;

    private final TicketRepository ticketRepo;

    private final KafkaTemplate<String, Object> kafka;

    public ParkingService( SpotAllocationStrategy allocation,
                           ParkingSpotRepository spotRepo,
                           TicketRepository ticketRepo,
                           KafkaTemplate<String, Object> kafka,
                           PricingStrategy pricing) {
        this.allocation = allocation;
        this.spotRepo = spotRepo;
        this.ticketRepo = ticketRepo;
        this.kafka = kafka;
        this.pricing = pricing;
    }

    @Transactional
    public Ticket park(String vehicleNumber, VehicleType type){
        if(ticketRepo.findActiveTicket(vehicleNumber).isPresent()){
            throw new AlreadyParkedException(vehicleNumber);
        }

        ParkingSpot spot = allocation
                .findSpot(type)
                .orElseThrow(() ->
                        new ParkingFullException(type));

        spot.occupy();
        spotRepo.save(spot);

        Ticket ticket = ticketRepo.save(new Ticket(vehicleNumber, type, spot));

        kafka.send(KafkaConfig.PARKED, vehicleNumber,
                new VehicleParkedEvent(
                        ticket.getId(), vehicleNumber,
                        type,
                        spot.getFloor().getFloorNumber(),
                        spot.getSpotNumber(),
                        ticket.getEntryTime()
                ));

        return ticket;
    }

    @Transactional
    public Ticket unpark(Long ticketId){
        Ticket ticket = ticketRepo
                .findById(ticketId)
                .orElseThrow(() ->
                        new TicketNotFoundException(
                                ticketId));

        if(!ticket.isActive()){
            throw new TicketAlreadyClosedException(
                    ticketId
            );
        }

        LocalDateTime exit = LocalDateTime.now();

        BigDecimal fee = pricing.calculateFee(ticket.getVehicleType(), ticket.getEntryTime(), exit);

        ticket.close(exit, fee);

        ParkingSpot spot = ticket.getSpot();
        spot.free();

        spotRepo.save(spot);
        ticketRepo.save(ticket);

        // Billing or receipts can listen to this
        // without parking knowing about them.
        kafka.send(KafkaConfig.EXITED,
                ticket.getVehicleNumber(),
                new VehicleExitedEvent(
                        ticket.getId(),
                        ticket.getVehicleNumber(),
                        ticket.getVehicleType(),
                        ticket.getEntryTime(),
                        exit,
                        fee));

        return ticket;

    }
}
