package no.ratneck.backend.controller;

import jakarta.validation.Valid;
import no.ratneck.backend.dto.OrderRequestDTO;
import no.ratneck.backend.dto.OrderResponseDTO;
import no.ratneck.backend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createOrder(@Valid @RequestBody OrderRequestDTO requestDTO){
        return orderService.createOrder(requestDTO);
    }
}
