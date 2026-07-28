package no.ratneck.backend.config;

import no.ratneck.backend.common.MerchType;
import no.ratneck.backend.common.Size;
import no.ratneck.backend.entity.Concert;
import no.ratneck.backend.entity.Merch;
import no.ratneck.backend.repository.ConcertRepository;
import no.ratneck.backend.repository.MerchRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    private final ConcertRepository concertRepository;
    private final MerchRepository merchRepository;

    public DatabaseSeeder(ConcertRepository concertRepository, MerchRepository merchRepository) {
        this.concertRepository = concertRepository;
        this.merchRepository = merchRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (concertRepository.count() == 0 && merchRepository.count() == 0){
            Concert concert1 = new Concert();

            concert1.setCity("Trondheim");
            concert1.setDate(LocalDateTime.of(2026, 9, 12, 20, 0));
            concert1.setTicketLink("konsert1.link");
            concert1.setVenue("Sentrum Scene");
            concert1.setTicketPrice(300.0);

            Concert concert2 = new Concert();

            concert2.setCity("Oslo");
            concert2.setDate(LocalDateTime.of(2026, 5, 20, 21, 0));
            concert2.setTicketLink("konsert2.link");
            concert2.setVenue("Oslo Scene");
            concert2.setTicketPrice(350.0);

            Concert concert3 = new Concert();

            concert3.setCity("Stavanger");
            concert3.setDate(LocalDateTime.of(2026, 1, 5, 20, 30));
            concert3.setTicketLink("konsert3.link");
            concert3.setVenue("Stavanger Scene");
            concert3.setTicketPrice(400.0);


            concertRepository.saveAll(List.of(concert1, concert2, concert3));


            Merch tshirt1 = new Merch();

            tshirt1.setMerchType(MerchType.T_SHIRT);
            tshirt1.setPrice(BigDecimal.valueOf(200.0));
            tshirt1.setSize(Size.M);
            tshirt1.setQuantity(40);

            Merch tshirt2 = new Merch();

            tshirt2.setMerchType(MerchType.T_SHIRT);
            tshirt2.setPrice(BigDecimal.valueOf(200.0));
            tshirt2.setSize(Size.S);
            tshirt2.setQuantity(40);

            Merch poster = new Merch();

            poster.setMerchType(MerchType.POSTER);
            poster.setQuantity(50);
            poster.setPrice(BigDecimal.valueOf(100.0));

            Merch cd = new Merch();

            cd.setMerchType(MerchType.CD);
            cd.setPrice(BigDecimal.valueOf(75.0));
            cd.setQuantity(150);


            Merch sweater = new Merch();

            sweater.setMerchType(MerchType.SWEATER);
            sweater.setSize(Size.L);
            sweater.setPrice(BigDecimal.valueOf(500.0));
            sweater.setQuantity(0);


            merchRepository.saveAll(List.of(tshirt1, tshirt2, poster, cd, sweater));



        }

    }
}
