package no.ratneck.backend.controller;


import no.ratneck.backend.dto.ConcertDTO;
import no.ratneck.backend.dto.ConcertRequestDTO;
import no.ratneck.backend.entity.Concert;
import no.ratneck.backend.exception.ConcertNotFoundException;
import no.ratneck.backend.service.ConcertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConcertController.class)
public class ConcertControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ConcertService concertService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_return_concert_when_concert_exists() throws Exception {
        //Given
        Long id = 1L;
        ConcertDTO mockConcert = new ConcertDTO(1L,"Spektrum",
                "Trondheim",
                LocalDateTime.of(2027, 8, 15, 20, 0),
                200.0,
                "link.no");

        when(concertService.getConcertById(id)).thenReturn(mockConcert);

        // When & Then

        mvc.perform(get("/api/concerts/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.venue").value("Spektrum"))
                .andExpect(jsonPath("$.city").value("Trondheim"))
                .andExpect(jsonPath("$.ticketPrice").value(200.0));
    }

    @Test
    public void should_throw_concert_not_found_exception_when_concert_not_found() throws Exception {

        when(concertService.getConcertById(999L)).thenThrow(new ConcertNotFoundException("Concert not found"));

        mvc.perform(get("/api/concerts/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Concert not found"));
    }

    @Test
    public void given_valid_concert_when_post_concert_returns_concert_dto() throws Exception {
        ConcertRequestDTO requestDTO = new ConcertRequestDTO();

        requestDTO.setVenue("Spektrum");
        requestDTO.setTicketLink("link.no");
        requestDTO.setTicketPrice(200.0);
        requestDTO.setDate(LocalDateTime.of(2027, 8, 15, 20, 0));
        requestDTO.setCity("Trondheim");


        ConcertDTO returnedDTO = new ConcertDTO(1L,"Spektrum",
                "Trondheim",
                LocalDateTime.of(2027, 8, 15, 20, 0),
                200.0,
                "link.no");

        when(concertService.createConcert(any(ConcertRequestDTO.class))).thenReturn(returnedDTO);

        mvc.perform(post("/api/concerts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.venue").value("Spektrum"))
                .andExpect(jsonPath("$.city").value("Trondheim"))
                .andExpect(jsonPath("$.ticketPrice").value(200.0));
    }

    @Test
    public void given_invalid_data_when_post_concert_returns_bad_request() throws Exception {
        ConcertRequestDTO invalidConcert = new ConcertRequestDTO();
        invalidConcert.setCity("Trondheim");
        invalidConcert.setTicketPrice(-200.0);
        invalidConcert.setTicketLink("link.no");

        mvc.perform(post("/api/concerts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidConcert)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.venue").exists())
                .andExpect(jsonPath("$.date").exists());

    }

    @Test
    public void given_valid_concerts_when_get_concerts_returns_ok() throws Exception{
        ConcertDTO concert1 = new ConcertDTO( 1L, "Spektrum", "Trondheim",
                LocalDateTime.of(2027, 8, 15, 20, 0),
                200.0, "link1.no");

        ConcertDTO concert2 = new ConcertDTO(2L, "Sentrum Scene", "Oslo",
                LocalDateTime.of(2027, 9, 20, 19, 0),
                350.0, "link2.no");

        when(concertService.getAllConcerts()).thenReturn(List.of(concert1, concert2));


        mvc.perform(get("/api/concerts"))
                .andExpect(jsonPath("$[0].venue").value("Spektrum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].city").value("Oslo"))
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
