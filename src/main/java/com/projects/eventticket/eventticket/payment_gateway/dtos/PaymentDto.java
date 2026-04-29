package com.projects.eventticket.eventticket.payment_gateway.dtos;

import com.projects.eventticket.eventticket.domain.enums.TicketPurchaseMethodEnum;
import com.projects.eventticket.eventticket.domain.enums.TicketPurchaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private TicketPurchaseStatusEnum status;
    private TicketPurchaseMethodEnum method;
    private String contact;
    private String responseString;
}
