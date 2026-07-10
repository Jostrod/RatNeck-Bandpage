package no.ratneck.backend.service;


import no.ratneck.backend.dto.MerchDTO;
import no.ratneck.backend.dto.MerchRequestDTO;
import no.ratneck.backend.entity.Merch;
import no.ratneck.backend.exception.ResourceNotFoundException;
import no.ratneck.backend.repository.MerchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MerchService {

    private final MerchRepository merchRepository;
    private static final Logger logger = LoggerFactory.getLogger(MerchService.class);

    @Autowired
    public MerchService(MerchRepository merchRepository) {
        this.merchRepository = merchRepository;
    }

    public List<MerchDTO> getAllMerch(){
        List<Merch> allMerch = merchRepository.findAll();
        logger.info("Returned all merch ");

        return allMerch.stream().map(merch -> new MerchDTO(
                merch.getId(), merch.getMerchType(), merch.getPrice(), merch.getQuantity() > 0, merch.getSize()
        )).toList();
    }


    public MerchDTO getMerchById(Long id){
        Merch foundMerch = merchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Merch", id));

        logger.info("Merch retrieved with id {}", id);

        return new MerchDTO(
                foundMerch.getId(),
                foundMerch.getMerchType(),
                foundMerch.getPrice(),
                foundMerch.getQuantity() > 0,
                foundMerch.getSize()
        );
    }

    public MerchDTO addMerch(MerchRequestDTO requestDTO){

        Merch merch = new Merch();

        merch.setMerchType(requestDTO.getMerchType());
        merch.setSize(requestDTO.getSize());
        merch.setPrice(requestDTO.getPrice());
        merch.setQuantity(requestDTO.getQuantity());

        Merch savedMerch = merchRepository.save(merch);
        logger.info("Merch created with id {}, type: {}", merch.getId(), merch.getMerchType());

        return new MerchDTO(savedMerch.getId(),
                savedMerch.getMerchType(),
                savedMerch.getPrice(),
                savedMerch.getQuantity() > 0,
                savedMerch.getSize());
    }

    public void deleteMerch(Long id){
        Merch foundMerch = merchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Merch", id));

        merchRepository.deleteById(foundMerch.getId());
        logger.info("merch with id: {} was successfully deleted", id);
    }

    public MerchDTO updateMerch(Long id, MerchRequestDTO requestDTO){
        Merch existingMerch = merchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Merch", id));

        existingMerch.setQuantity(requestDTO.getQuantity());
        existingMerch.setMerchType(requestDTO.getMerchType());
        existingMerch.setSize(requestDTO.getSize());
        existingMerch.setPrice(requestDTO.getPrice());

        merchRepository.save(existingMerch);
        logger.info("Merch with id {} successfully updated", existingMerch.getId());
        return new MerchDTO(
                existingMerch.getId(),
                existingMerch.getMerchType(),
                existingMerch.getPrice(),
                existingMerch.getQuantity() > 0,
                existingMerch.getSize()
        );
    }
}
