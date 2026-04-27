package com.projects.eventticket.eventticket.payment_gateway.service;

import com.projects.eventticket.eventticket.payment_gateway.dtos.OrderDto;
import com.razorpay.RazorpayException;

public interface PaymentGatewayService {
    OrderDto createOrder(int amount, String currency) throws RazorpayException;
}
