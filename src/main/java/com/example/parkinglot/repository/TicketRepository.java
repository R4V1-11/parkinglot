package com.example.parkinglot.repository;

import com.example.parkinglot.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("""
        SELECT ticket FROM Ticket ticket
        WHERE ticket.vehicleNumber = :vehicleNumber
          AND ticket.exitTime IS NULL
        """)
    Optional<Ticket> findActiveTicket(@Param("vehicleNumber")String vehicleNumber);
}
