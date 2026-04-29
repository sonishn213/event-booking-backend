package com.projects.eventticket.eventticket.domain.entity;

import com.projects.eventticket.eventticket.domain.enums.TicketPurchaseMethodEnum;
import com.projects.eventticket.eventticket.domain.enums.TicketPurchaseStatusEnum;
import com.projects.eventticket.eventticket.domain.enums.TicketStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;
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
    private TicketPurchaseStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="purchaser_id")
    private User purchaser;

    @Column(name="customer_name",nullable = false)
    private String customerName;

    @Column(name="customer_email",nullable = false)
    private String customerEmail;

    @Column(name="customer_contact")
    private String customerContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_type_id")
    private TicketType ticketType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_id")
    private Ticket ticket;

    @Column(name="amount",nullable = false)
    private int amount;

    @Column(name="currency",nullable = false)
    private String currency;

    @Column(name="method")
    @Enumerated(EnumType.STRING)
    private TicketPurchaseMethodEnum method;

    @Column(name="razorpay_payment_id")
    private String razorpayPaymentId;

    @Column(name="razorpay_order_id")
    private String razorpayOrderId;

    @Column(name="razorpay_signature")
    private String razorpaySignature;

    @Column(name="razorpay_payment_response",columnDefinition = "TEXT")
    private String razorpayPaymentResponse;

    @CreatedDate
    @Column(name="created_at",updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TicketPurchase that = (TicketPurchase) o;
        return amount == that.amount && Objects.equals(id, that.id) && status == that.status && Objects.equals(purchaser, that.purchaser) && Objects.equals(customerName, that.customerName) && Objects.equals(customerEmail, that.customerEmail) && Objects.equals(customerContact, that.customerContact) && Objects.equals(ticketType, that.ticketType) && Objects.equals(ticket, that.ticket) && Objects.equals(currency, that.currency) && method == that.method && Objects.equals(razorpayPaymentId, that.razorpayPaymentId) && Objects.equals(razorpayOrderId, that.razorpayOrderId) && Objects.equals(razorpaySignature, that.razorpaySignature) && Objects.equals(razorpayPaymentResponse, that.razorpayPaymentResponse) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, purchaser, customerName, customerEmail, customerContact, ticketType, ticket, amount, currency, method, razorpayPaymentId, razorpayOrderId, razorpaySignature, razorpayPaymentResponse, createdAt, updatedAt);
    }
}
