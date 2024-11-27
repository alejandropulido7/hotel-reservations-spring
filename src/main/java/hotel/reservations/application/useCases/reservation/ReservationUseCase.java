package hotel.reservations.application.useCases.reservation;

import hotel.reservations.domain.exceptions.ApplicationException;
import hotel.reservations.domain.gateway.ReservationGateway;
import hotel.reservations.domain.model.Reservation;
import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations.ReservationRespositoryAdapter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReservationUseCase {

    private final ReservationGateway reservationGateway;

    public ReservationUseCase(ReservationGateway reservationGateway) {
        this.reservationGateway = reservationGateway;
    }

    public Mono<Reservation> bookReservation(Reservation reservation){
        return reservationGateway.getReservationByIRoomAndCheckIn(reservation.getIdRoom(), reservation.getCheckIn())
                .collectList()
                .flatMap(reservations -> {
                    if(!reservations.isEmpty()){
                        return Mono.error(new ApplicationException("Ya hay una reserva para la fecha "+reservations.get(0).getCheckIn()
                        +", puedes realizar la reserva a partir de la fecha "+reservations.get(0).getCheckOut().plusDays(1)));
                    }
                    return Mono.just(reservations);
                })
                .flatMap(reservations -> reservationGateway.bookReservation(reservation));
    }
}
