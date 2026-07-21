package no.ratneck.backend.service;

import no.ratneck.backend.common.MerchType;
import no.ratneck.backend.common.Size;
import no.ratneck.backend.dto.MerchDTO;
import no.ratneck.backend.dto.MerchRequestDTO;
import no.ratneck.backend.entity.Merch;
import no.ratneck.backend.exception.ResourceNotFoundException;
import no.ratneck.backend.repository.MerchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MerchServiceTest {

    @Mock
    private MerchRepository merchRepository;

    @InjectMocks
    private MerchService merchService;


    @Test
    public void given_merch_exists_when_get_merch_service_returns_merch_dto(){
        Merch merch = new Merch();

        merch.setMerchType(MerchType.T_SHIRT);
        merch.setPrice(BigDecimal.valueOf(100.0));
        merch.setId(1L);
        merch.setQuantity(100);
        merch.setSize(Size.M);

        when(merchRepository.findById(1L)).thenReturn(Optional.of(merch));

        MerchDTO response = merchService.getMerchById(1L);

        assertEquals(MerchType.T_SHIRT, response.getMerchType());
        assertEquals(BigDecimal.valueOf(100.0), response.getPrice());
        assertEquals(1L, response.getId());
        assertEquals(Size.M, response.getSize());
        assertTrue(response.isInStock());
    }

    @Test
    public void given_merch_not_exists_when_get_merch_service_throws_merch_not_found(){

        when(merchRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> merchService.getMerchById(999L));
    }

    @Test
    public void given_merch_id_not_found_when_update_merch_throws_merch_not_found(){
        when(merchRepository.findById(999L)).thenReturn(Optional.empty());
        MerchRequestDTO requestDTO = new MerchRequestDTO();
        assertThrows(ResourceNotFoundException.class, () -> merchService.updateMerch(999L, requestDTO));
    }

    @Test
    public void given_merch_id_found_when_update_merch_service_return_merch_dto(){
        ArgumentCaptor<Merch> merchCaptor = ArgumentCaptor.forClass(Merch.class);
        Merch existingMerch = new Merch();

        existingMerch.setSize(Size.S);
        existingMerch.setMerchType(MerchType.T_SHIRT);
        existingMerch.setPrice(BigDecimal.valueOf(200.0));
        existingMerch.setQuantity(100);
        existingMerch.setId(1L);

        MerchRequestDTO requestDTO = new MerchRequestDTO();

        requestDTO.setMerchType(MerchType.T_SHIRT);
        requestDTO.setSize(Size.M);
        requestDTO.setPrice(BigDecimal.valueOf(250.0));
        requestDTO.setQuantity(200);

        when(merchRepository.findById(1L)).thenReturn(Optional.of(existingMerch));

        MerchDTO response = merchService.updateMerch(1L, requestDTO);

        assertEquals(Size.M, response.getSize());
        assertTrue(response.isInStock());
        assertEquals(BigDecimal.valueOf(250.0), response.getPrice());

        verify(merchRepository).save(merchCaptor.capture());
        Merch captured = merchCaptor.getValue();

        assertEquals(Size.M, captured.getSize());
        assertEquals(200, captured.getQuantity());
        assertEquals(BigDecimal.valueOf(250.0), captured.getPrice());
    }

    @Test
    public void given_merch_id_not_found_when_delete_merch_service_return_merch_not_found_exception(){
        when(merchRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> merchService.deleteMerch(999L));
    }

    @Test
    public void given_merch_id_found_when_delete_merch_service_calls_delete(){
        Merch existingMerch = new Merch();
        existingMerch.setId(1L);

        when(merchRepository.findById(1L)).thenReturn(Optional.of(existingMerch));
        merchService.deleteMerch(1L);
        verify(merchRepository).deleteById(1L);
    }

    @Test
    public void given_valid_request_when_create_merch_service_maps_all_fields(){
        ArgumentCaptor<Merch> capturedMerch = ArgumentCaptor.forClass(Merch.class);

        MerchRequestDTO requestDTO = new MerchRequestDTO();
        requestDTO.setPrice(BigDecimal.valueOf(300.0));
        requestDTO.setQuantity(100);
        requestDTO.setMerchType(MerchType.T_SHIRT);
        requestDTO.setSize(Size.M);



        Merch savedMerch = new Merch();

        savedMerch.setMerchType(MerchType.T_SHIRT);
        savedMerch.setPrice(BigDecimal.valueOf(300.0));
        savedMerch.setQuantity(100);
        savedMerch.setSize(Size.M);
        savedMerch.setId(1L);


        when(merchRepository.save(any(Merch.class))).thenReturn(savedMerch);

        MerchDTO response = merchService.addMerch(requestDTO);
        verify(merchRepository).save(capturedMerch.capture());

        Merch captured = capturedMerch.getValue();

        assertEquals(MerchType.T_SHIRT, captured.getMerchType());
        assertEquals(100, captured.getQuantity());
        assertEquals(BigDecimal.valueOf(300.0), captured.getPrice());

        assertEquals(MerchType.T_SHIRT, response.getMerchType());
        assertTrue(response.isInStock());
        assertEquals(BigDecimal.valueOf(300.0), response.getPrice());
        assertEquals(1L, response.getId());


    }



    @Test
    public void given_merch_exists_when_get_all_merch_service_returns_list_of_dtos(){
        Merch merchOne = new Merch();
        merchOne.setId(1L);
        merchOne.setSize(Size.S);
        merchOne.setQuantity(100);
        merchOne.setPrice(BigDecimal.valueOf(200.0));
        merchOne.setMerchType(MerchType.T_SHIRT);


        Merch merchTwo = new Merch();
        merchTwo.setId(2L);
        merchTwo.setQuantity(0);
        merchTwo.setPrice(BigDecimal.valueOf(100.0));
        merchTwo.setMerchType(MerchType.POSTER);

        when(merchRepository.findAll()).thenReturn(List.of(merchOne, merchTwo));

        List<MerchDTO> response = merchService.getAllMerch();

        assertEquals(2, response.size());

        assertEquals(Size.S, response.get(0).getSize());
        assertTrue(response.get(0).isInStock());
        assertEquals(BigDecimal.valueOf(200.0), response.get(0).getPrice());
        assertEquals(MerchType.T_SHIRT, response.get(0).getMerchType());

        assertEquals(2L, response.get(1).getId());
        assertEquals(MerchType.POSTER, response.get(1).getMerchType());
        assertFalse(response.get(1).isInStock());
        assertEquals(BigDecimal.valueOf(100.0), response.get(1).getPrice());

    }
}
