package hotel.reservations.infraestructure.web.reservation;

import hotel.reservations.application.useCases.reservation.ReservationUseCase;
import hotel.reservations.infraestructure.web.reservation.dto.ReservationDTO;
import hotel.reservations.infraestructure.web.reservation.transformers.ReservationTransformersDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationService {

    private final ReservationUseCase reservationUseCase;
    private final ReservationTransformersDTO reservationTransformer;

    public ReservationService(ReservationUseCase reservationUseCase, ReservationTransformersDTO reservationTransformer) {
        this.reservationUseCase = reservationUseCase;
        this.reservationTransformer = reservationTransformer;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping()
    public Mono<ReservationDTO> bookReservation(@Valid @RequestBody ReservationDTO reservationDTO){
        System.out.println(reservationDTO);
        return reservationUseCase.bookReservation(reservationTransformer.dtoToDomain(reservationDTO))
                .map(reservationTransformer::domainToDto);
    }
}
