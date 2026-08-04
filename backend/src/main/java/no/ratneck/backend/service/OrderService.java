package no.ratneck.backend.service;


import no.ratneck.backend.dto.OrderLineRequestDTO;
import no.ratneck.backend.dto.OrderLineResponseDTO;
import no.ratneck.backend.dto.OrderRequestDTO;
import no.ratneck.backend.dto.OrderResponseDTO;
import no.ratneck.backend.entity.Merch;
import no.ratneck.backend.entity.Order;
import no.ratneck.backend.entity.OrderLine;
import no.ratneck.backend.exception.ResourceNotFoundException;
import no.ratneck.backend.repository.MerchRepository;
import no.ratneck.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MerchRepository merchRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    @Autowired
    public OrderService(OrderRepository orderRepository, MerchRepository merchRepository) {
        this.orderRepository = orderRepository;
        this.merchRepository = merchRepository;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO request){
        Order order = new Order();
        for (OrderLineRequestDTO lineRequest : request.lines()) {
            Long productId = lineRequest.productId();
            Merch merch = merchRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Merch", productId));
            OrderLine orderLine = new OrderLine();
            order.getOrderLines().add(orderLine);

            orderLine.setMerch(merch);
            orderLine.setQuantity(lineRequest.quantity());
            orderLine.setPriceAtPurchase(merch.getPrice());

            orderLine.setOrder(order);

        }
        Order savedOrder = orderRepository.save(order);
        BigDecimal total = savedOrder.getOrderLines().stream().map(
                line ->
                        line.getPriceAtPurchase().multiply(BigDecimal.valueOf(line.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<OrderLineResponseDTO> lineDTOs = savedOrder.getOrderLines().stream().map(line ->
                new OrderLineResponseDTO(line.getMerch().getId(), line.getMerch().getMerchType(),
                        line.getQuantity(), line.getPriceAtPurchase())).toList();

        return new OrderResponseDTO(savedOrder.getId(), savedOrder.getCreatedAt(), lineDTOs, total);
    }



}
