package com.projects.eventticket.eventticket.repository;

import com.projects.eventticket.eventticket.domain.entity.TicketPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketPurchaseRepository extends JpaRepository<TicketPurchase, UUID> {
    TicketPurchase findByRazorpayOrderId(String id);
}
