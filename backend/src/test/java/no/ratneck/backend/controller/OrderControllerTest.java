package no.ratneck.backend.controller;


import no.ratneck.backend.dto.OrderResponseDTO;
import no.ratneck.backend.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void given_valid_order_when_postOrder_returns_201() throws Exception {
        OrderResponseDTO responseDTO = new OrderResponseDTO(1L, LocalDateTime.now(), List.of(), BigDecimal.ZERO);

        when(orderService.createOrder(any())).thenReturn(responseDTO);

        String jsonBody = """
                {"lines": [{ "productId": 1, "quantity": 1}]}
                """;

        mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void given_invalid_order_when_postOrder_returns_400() throws Exception {
        String jsonBody = """
                {"lines": []}
                """;
        mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
        verify(orderService, never()).createOrder(any());
    }
}
