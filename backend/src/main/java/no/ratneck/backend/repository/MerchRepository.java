package no.ratneck.backend.repository;

import no.ratneck.backend.entity.Merch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MerchRepository extends JpaRepository<Merch, Long> {
    List<Merch> id(Long id);
}
