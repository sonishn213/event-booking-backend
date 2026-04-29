package com.projects.eventticket.eventticket.payment_gateway.mapper;

import com.projects.eventticket.eventticket.payment_gateway.dtos.OrderDto;
import com.razorpay.Order;
import org.json.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Map;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    default OrderDto toOrderDto(Order order) {
        if (order == null) return null;

        return new OrderDto(
                order.get("id"),
                order.get("amount") != null ? ((Number) order.get("amount")).intValue() : 0,
                order.get("currency")
        );
    }
}
