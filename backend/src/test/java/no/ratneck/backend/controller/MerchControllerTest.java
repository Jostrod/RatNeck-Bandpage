package no.ratneck.backend.controller;

import no.ratneck.backend.common.MerchType;
import no.ratneck.backend.common.Size;
import no.ratneck.backend.dto.MerchDTO;
import no.ratneck.backend.service.MerchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@WebMvcTest(MerchController.class)
public class MerchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private MerchService merchService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void should_return_merch_when_merch_exists()throws Exception {
        Long id = 1L;

        MerchDTO mockMerch = new MerchDTO(1L, MerchType.T_SHIRT, BigDecimal.valueOf(200.0), true, Size.L);

        when(merchService.getMerchById(id)).thenReturn(mockMerch);

        mvc.perform(get("/api/merch/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size").value("L"))
                .andExpect(jsonPath("$.merchType").value("T_SHIRT"));
    }

    @Test
    public void should_throw_mech_not_found_exception_when_mech_not_found() throws Exception{

    }
}
