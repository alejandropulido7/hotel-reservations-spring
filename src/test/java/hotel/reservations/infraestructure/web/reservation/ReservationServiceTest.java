package hotel.reservations.infraestructure.web.reservation;

import hotel.reservations.application.useCases.reservation.ReservationUseCase;
import hotel.reservations.domain.model.Reservation;
import hotel.reservations.infraestructure.web.reservation.dto.ReservationDTO;
import hotel.reservations.infraestructure.web.reservation.transformers.ReservationTransformersDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ReservationService.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ReservationUseCase reservationUseCase;

    @MockBean
    ReservationTransformersDTO reservationTransformersDTO;

    private Reservation reservationMock = Reservation.builder()
            .checkIn(LocalDateTime.now())
            .idRoom(123L)
            .nameUser("User test")
            .build();


    private ReservationDTO reservationMockDTO = new ReservationDTO();

    @BeforeEach
    public void setUp(){
        reservationMockDTO.setCheckIn(plusDate(1L));
        reservationMockDTO.setCheckOut(plusDate(3L));
        reservationMockDTO.setIdRoom(123L);

        Mockito.when(reservationUseCase.bookReservation(any())).thenReturn(Mono.just(reservationMock));
        Mockito.when(reservationTransformersDTO.dtoToDomain(any())).thenReturn(reservationMock);
        Mockito.when(reservationTransformersDTO.domainToDto(any())).thenReturn(reservationMockDTO);
    }

    private Date plusDate(Long days){
        LocalDateTime local = LocalDate.now().plusDays(days).atStartOfDay();
        return Date.from(local.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void bookReservation() {

        WebTestClient.ResponseSpec exchange = webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(reservationMockDTO))
                .exchange();


        exchange.expectStatus().isOk().expectBody(ReservationDTO.class)
                .consumeWith(reservationDTOEntityExchangeResult -> {
                    Assertions.assertEquals(reservationDTOEntityExchangeResult.getResponseBody().getIdRoom(), reservationMockDTO.getIdRoom());
                });
    }
}