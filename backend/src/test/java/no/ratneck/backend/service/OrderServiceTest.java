package no.ratneck.backend.service;


import no.ratneck.backend.dto.OrderLineRequestDTO;
import no.ratneck.backend.dto.OrderRequestDTO;
import no.ratneck.backend.dto.OrderResponseDTO;
import no.ratneck.backend.entity.Merch;
import no.ratneck.backend.entity.Order;
import no.ratneck.backend.exception.ResourceNotFoundException;
import no.ratneck.backend.repository.MerchRepository;
import no.ratneck.backend.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    MerchRepository merchRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;


    @Test
    public void given_validRequest_when_createOrder_returns_order_with_correct_lines(){
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        OrderLineRequestDTO lineRequestDTO = new OrderLineRequestDTO(1L, 1);

        OrderRequestDTO requestDTO = new OrderRequestDTO(List.of(lineRequestDTO));

        Merch merch = new Merch();
        merch.setPrice(BigDecimal.valueOf(500.0));

        when(merchRepository.findById(1L)).thenReturn(Optional.of(merch));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        OrderResponseDTO result = orderService.createOrder(requestDTO);

        verify(orderRepository).save(orderCaptor.capture());
        Order captured = orderCaptor.getValue();

        assertEquals(0, captured.getOrderLines().get(0).getPriceAtPurchase().compareTo(BigDecimal.valueOf(500.0)));
        assertEquals(1, captured.getOrderLines().get(0).getQuantity());
    }

    @Test
    public void given_unknown_productID_when_createOrder_throws_ResourceNotFound_exception() {

        OrderLineRequestDTO lineRequestDTO = new OrderLineRequestDTO(1L, 1);

        OrderRequestDTO requestDTO = new OrderRequestDTO(List.of(lineRequestDTO));

        when(merchRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(requestDTO));
        verify(orderRepository, never()).save(any(Order.class));


    }

}
