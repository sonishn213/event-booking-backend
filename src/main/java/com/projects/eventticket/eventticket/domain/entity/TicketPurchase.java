package com.projects.eventticket.eventticket.domain.entity;

import com.projects.eventticket.eventticket.domain.enums.TicketStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="ticket_purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketPurchase {
    @Id
    @Column(name="id",updatable = false,nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatusEnum status;
}
