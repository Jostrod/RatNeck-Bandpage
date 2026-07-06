package no.ratneck.backend.service;

import no.ratneck.backend.dto.ConcertDTO;
import no.ratneck.backend.dto.ConcertRequestDTO;
import no.ratneck.backend.entity.Concert;
import no.ratneck.backend.exception.ConcertNotFoundException;
import no.ratneck.backend.repository.ConcertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ConcertServiceTest {

    @Mock //What we are mocking the response of
    private ConcertRepository concertRepository;

    @InjectMocks //The thing we are testing
    private ConcertService concertService;

    @BeforeEach //Stuff we need before the test runs
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_concert_exists_when_get_concert_service_return_concert_dto(){
        //Given
        Concert concert = new Concert();
        concert.setId((long) 1);
        concert.setTicketPrice(200);
        concert.setCity("Trondheim");
        concert.setTicketLink("link.to.ticket.no");
        concert.setDate(LocalDateTime.of(2027, 8, 15, 20, 0));
        concert.setVenue("Spektrum");

        when(concertRepository.findById(1L)).thenReturn(Optional.of(concert));
        //When
        ConcertDTO response = concertService.getConcertById(1L);
        //Then
        assertEquals("Spektrum", response.getVenue());
        assertEquals("link.to.ticket.no", response.getTicketLink());
        assertEquals(200.0, response.getTicketPrice());
        assertEquals("Trondheim", response.getCity());
    }

    @Test
    public void given_concert_not_exists_when_get_concert_service_throws_concert_not_found(){

        when(concertRepository.findById(999L)).thenReturn(Optional.empty());

        //Then
        assertThrows(ConcertNotFoundException.class, () -> concertService.getConcertById(999L));
    }

    @Test
    public void given_concert_id_not_found_when_update_concert_service_throws_concert_not_found(){
        when(concertRepository.findById(999L)).thenReturn(Optional.empty());
        ConcertRequestDTO dto = new ConcertRequestDTO();
        assertThrows(ConcertNotFoundException.class, () -> concertService.updateConcert(999L, dto));
    }

    @Test
    public void given_concert_id_found_when_update_concert_service_return_concert_dto(){
        Concert excistingConcert = new Concert();
        excistingConcert.setVenue("Spektrum");
        excistingConcert.setId(1L);
        excistingConcert.setCity("Trondheim");
        excistingConcert.setDate(LocalDateTime.of(2027, 8, 15, 20, 0));
        excistingConcert.setTicketPrice(250.0);
        excistingConcert.setTicketLink("ticketlink.no");

        ConcertRequestDTO requestDTO = new ConcertRequestDTO();
        requestDTO.setCity("Oslo");
        requestDTO.setVenue("Telenor Arena");
        requestDTO.setDate(LocalDateTime.of(2027, 8, 15, 21, 0));
        requestDTO.setTicketPrice(500.0);
        requestDTO.setTicketLink("newTicketLink.no");

        when(concertRepository.findById(1L)).thenReturn(Optional.of(excistingConcert));

        ConcertDTO response = concertService.updateConcert(1L, requestDTO);


        assertEquals("Oslo", response.getCity());
        assertEquals("Telenor Arena", response.getVenue());
        assertEquals("newTicketLink.no", response.getTicketLink());
        assertEquals(500.0, response.getTicketPrice());
    }

    @Test
    public void given_concert_id_not_found_when_delete_concert_service_return_concert_not_found_exception(){
        when(concertRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ConcertNotFoundException.class, () -> concertService.deleteConcert(999L));
    }

    @Test
    public void given_concert_id_found_when_delete_concert_service_calls_delete(){
        Concert excistingConcert = new Concert();
        excistingConcert.setId(1L);

        when(concertRepository.findById(1L)).thenReturn(Optional.of(excistingConcert));
        concertService.deleteConcert(1L);
        verify(concertRepository).deleteById(1L);

    }

    @Test
    public void given_valid_request_when_create_concert_service_maps_all_fields(){
        ArgumentCaptor<Concert> capturedConcert = ArgumentCaptor.forClass(Concert.class);

        ConcertRequestDTO requestDTO = new ConcertRequestDTO();
        requestDTO.setCity("Trondheim");
        requestDTO.setVenue("Spektrum");
        requestDTO.setDate(LocalDateTime.of(2027, 8, 15, 21, 0));
        requestDTO.setTicketLink("link.no");
        requestDTO.setTicketPrice(200.0);


        Concert savedConcert = new Concert();

        savedConcert.setCity("Trondheim");
        savedConcert.setVenue("Spektrum");
        savedConcert.setDate(LocalDateTime.of(2027, 8, 15, 21, 0));
        savedConcert.setTicketLink("link.no");
        savedConcert.setTicketPrice(200.0);

        when(concertRepository.save(any(Concert.class))).thenReturn(savedConcert);

        ConcertDTO response = concertService.createConcert(requestDTO);
        verify(concertRepository).save(capturedConcert.capture());

        Concert captured = capturedConcert.getValue();

        assertEquals("Trondheim", captured.getCity());
        assertEquals("Spektrum", captured.getVenue());
        assertEquals("link.no", captured.getTicketLink());
        assertEquals(200.0, captured.getTicketPrice());

        assertEquals("Trondheim", response.getCity());
        assertEquals("Spektrum", response.getVenue());
        assertEquals("link.no", response.getTicketLink());
        assertEquals(200.0, response.getTicketPrice());
    }
}
