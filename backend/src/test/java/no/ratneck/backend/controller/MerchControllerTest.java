package no.ratneck.backend.controller;

import no.ratneck.backend.common.MerchType;
import no.ratneck.backend.common.Size;
import no.ratneck.backend.dto.MerchDTO;
import no.ratneck.backend.dto.MerchRequestDTO;
import no.ratneck.backend.exception.ResourceNotFoundException;
import no.ratneck.backend.service.MerchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import static org.mockito.ArgumentMatchers.any;
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

        when(merchService.getMerchById(999L)).thenThrow(new ResourceNotFoundException("Merch", 999L));

        mvc.perform(get("/api/merch/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No Merch found with ID 999"));
    }

    @Test
    public void given_valid_merch_when_post_merch_returns_merch_dto()throws Exception{

        MerchRequestDTO requestDTO = new MerchRequestDTO();

        requestDTO.setMerchType(MerchType.T_SHIRT);
        requestDTO.setPrice(BigDecimal.valueOf(300.0));
        requestDTO.setQuantity(100);
        requestDTO.setSize(Size.L);

        MerchDTO returnedDTO = new MerchDTO(
                1L,
                MerchType.T_SHIRT,
                BigDecimal.valueOf(300.0),
                true,
                Size.L);

        when(merchService.addMerch(any(MerchRequestDTO.class))).thenReturn(returnedDTO);

        mvc.perform(post("/api/merch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merchType").value("T_SHIRT"))
                .andExpect(jsonPath("$.size").value("L"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(300.0)));
    }

    @Test
    public void given_invalid_data_when_add_merch_returns_bad_request()throws Exception{
        MerchRequestDTO invalidMerch = new MerchRequestDTO();


        invalidMerch.setPrice(BigDecimal.valueOf(-222.0));
        invalidMerch.setQuantity(-2);


        mvc.perform(post("/api/merch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidMerch)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.size").exists())
                .andExpect(jsonPath("$.merchType").exists());

    }










}
