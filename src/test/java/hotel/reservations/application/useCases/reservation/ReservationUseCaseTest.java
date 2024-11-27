package hotel.reservations.application.useCases.reservation;

import hotel.reservations.domain.exceptions.ApplicationException;
import hotel.reservations.domain.gateway.ReservationGateway;
import hotel.reservations.domain.model.Reservation;
import org.junit.jupiter.api.Assertions;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ReservationUseCaseTest {

    @Mock
    ReservationGateway reservationGateway;

    @InjectMocks
    ReservationUseCase reservationUseCase;

    Reservation reservation = Reservation.builder()
            .idUser("123")
            .idRoom(1L)
            .checkIn(LocalDateTime.now())
            .checkOut(LocalDateTime.now())
            .build();

    @Test
    public void bookReservationError() {
        Mockito.when(reservationGateway.getReservationByIRoomAndCheckIn(any(), any())).thenReturn(Flux.just(reservation));

        Mono<Reservation> reservationMono = reservationUseCase.bookReservation(reservation);

        StepVerifier.create(reservationMono).expectError(ApplicationException.class).verify();
    }

    @Test
    public void bookReservation() {
        Mockito.when(reservationGateway.getReservationByIRoomAndCheckIn(any(), any())).thenReturn(Flux.empty());
        Mockito.when(reservationGateway.bookReservation(any())).thenReturn(Mono.just(reservation));

        Mono<Reservation> reservationMono = reservationUseCase.bookReservation(reservation);

        StepVerifier.create(reservationMono).consumeNextWith(reservation1 -> {
            Assertions.assertEquals(reservation1.getIdRoom(), reservation.getIdRoom());
            Assertions.assertEquals(reservation1.getCheckIn(), reservation.getCheckIn());
        }).verifyComplete();
    }
}