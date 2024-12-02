package com.flightreservation.flight_reservation_system.controller;

import com.flightreservation.flight_reservation_system.dto.input.order.CreateOrderInput;
import com.flightreservation.flight_reservation_system.dto.payload.order.OrderPayload;
import com.flightreservation.flight_reservation_system.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@SecurityRequirement(name = "bearer Auth")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderPayload> createOrder(CreateOrderInput createOrderInput){
        return ResponseEntity.ok(orderService.createOrder(createOrderInput));
    }

}
