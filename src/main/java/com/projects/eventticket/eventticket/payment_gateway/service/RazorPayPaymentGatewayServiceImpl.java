package com.projects.eventticket.eventticket.payment_gateway.service;

import com.projects.eventticket.eventticket.config_properties.RazorPayConfigProperties;
import com.projects.eventticket.eventticket.payment_gateway.dtos.CallBackDto;
import com.projects.eventticket.eventticket.payment_gateway.dtos.OrderDto;
import com.projects.eventticket.eventticket.payment_gateway.dtos.PaymentDto;
import com.projects.eventticket.eventticket.payment_gateway.mapper.OrderMapper;
import com.projects.eventticket.eventticket.payment_gateway.mapper.PaymentMapper;
import com.razorpay.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorPayPaymentGatewayServiceImpl implements PaymentGatewayService{

    private final RazorPayConfigProperties razorPayConfig;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;

    @Override
    public OrderDto createOrder(int amount, String currency) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorPayConfig.key_id(), razorPayConfig.key_secret());
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency",currency);
        Order order = razorpay.orders.create(orderRequest);
        System.out.println(order.toString());
        return orderMapper.toOrderDto(order);
    }

    @Override
    public boolean verifyPurchase(CallBackDto RazorPayCallbackResponse)
            throws RazorpayException {
        // Verify the payment signature here
        String signature = RazorPayCallbackResponse.getOrderId() + "|" + RazorPayCallbackResponse.getPaymentId();
        return Utils.verifySignature(signature, RazorPayCallbackResponse.getSignature(), razorPayConfig.key_secret());
    }

    @Override
    public PaymentDto getPaymentById(String paymentId) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorPayConfig.key_id(), razorPayConfig.key_secret());

        return paymentMapper.toPaymentDto(razorpay.payments.fetch(paymentId));
    }
}
