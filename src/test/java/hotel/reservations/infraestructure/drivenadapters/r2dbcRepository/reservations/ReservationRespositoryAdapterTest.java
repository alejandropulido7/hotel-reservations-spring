package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations;

import hotel.reservations.domain.model.Reservation;
import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations.transformers.ReservationTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ReservationRespositoryAdapterTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    ReservationTransformer reservationTransformer;

    @InjectMocks
    ReservationRespositoryAdapter reservationRespositoryAdapter;

    ReservationEntity reservationEntity = ReservationEntity.builder()
            .checkIn(LocalDateTime.now())
            .idUser("123")
            .build();

    Reservation reservation = Reservation.builder()
            .checkIn(LocalDateTime.now())
            .idUser("321")
            .build();

    @BeforeEach
    public void setUp(){
        Mockito.when(reservationTransformer.domainToEntity(any())).thenReturn(reservationEntity);
        Mockito.when(reservationTransformer.entityToDomain(any())).thenReturn(reservation);
    }

    @Test
    public void bookReservation() {
        Mockito.when(reservationRepository.save(any())).thenReturn(Mono.just(reservationEntity));

        Mono<Reservation> reservationMono = reservationRespositoryAdapter.bookReservation(reservation);

        StepVerifier.create(reservationMono)
                .consumeNextWith(reservation1 -> {
                    Assertions.assertEquals(reservation1.getIdUser(), reservation.getIdUser());
                    Assertions.assertEquals(reservation1.getCheckIn(), reservation.getCheckIn());
                }).verifyComplete();
    }

    @Test
    public void getReservationByIRoomAndCheckIn() {
        Mockito.when(reservationRepository.findByIdRoomAndCheckIn(any(), any())).thenReturn(Flux.just(reservationEntity));

        Flux<Reservation> reservationByIRoomAndCheckIn = reservationRespositoryAdapter
                .getReservationByIRoomAndCheckIn(123L, LocalDateTime.now());

        StepVerifier.create(reservationByIRoomAndCheckIn)
                .consumeNextWith(reservation1 -> {
                    Assertions.assertEquals(reservation1.getCheckIn(), reservation.getCheckIn());
                    Assertions.assertEquals(reservation1.getIdUser(), reservation.getIdUser());
                }).verifyComplete();
    }
}