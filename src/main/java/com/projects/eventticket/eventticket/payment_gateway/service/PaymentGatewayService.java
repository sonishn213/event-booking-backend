package com.projects.eventticket.eventticket.payment_gateway.service;

import com.projects.eventticket.eventticket.payment_gateway.dtos.CallBackDto;
import com.projects.eventticket.eventticket.payment_gateway.dtos.OrderDto;
import com.projects.eventticket.eventticket.payment_gateway.dtos.PaymentDto;
import com.razorpay.Payment;
import com.razorpay.RazorpayException;

public interface PaymentGatewayService {
    OrderDto createOrder(int amount, String currency) throws RazorpayException;

    boolean verifyPurchase(CallBackDto RazorPayCallbackResponse) throws RazorpayException;

    PaymentDto getPaymentById(String paymentId) throws RazorpayException;
}
