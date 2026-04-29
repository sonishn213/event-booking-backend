package com.projects.eventticket.eventticket.payment_gateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallBackDto {
    private String orderId;
    private String paymentId;
    private String signature;
}
