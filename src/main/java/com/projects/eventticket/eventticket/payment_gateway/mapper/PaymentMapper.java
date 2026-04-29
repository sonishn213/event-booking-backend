package com.projects.eventticket.eventticket.payment_gateway.mapper;

import com.projects.eventticket.eventticket.domain.enums.TicketPurchaseMethodEnum;
import com.projects.eventticket.eventticket.domain.enums.TicketPurchaseStatusEnum;
import com.projects.eventticket.eventticket.payment_gateway.dtos.PaymentDto;
import com.razorpay.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    default PaymentDto toPaymentDto(Payment payment) {
        if (payment == null) return null;

        TicketPurchaseStatusEnum status = TicketPurchaseStatusEnum.valueOf(payment.get("status").toString().toUpperCase());

        TicketPurchaseMethodEnum method = TicketPurchaseMethodEnum.valueOf(payment.get("method").toString().toUpperCase());

        return new PaymentDto(
            status,
            method,
            payment.get("contact"),
            payment.toString()
        );
    }
}
