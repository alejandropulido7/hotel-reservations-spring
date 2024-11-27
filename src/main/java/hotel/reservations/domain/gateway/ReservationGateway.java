package hotel.reservations.domain.gateway;

import hotel.reservations.domain.model.Reservation;
import hotel.reservations.domain.model.SearchRooms;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface ReservationGateway {
    public Mono<Reservation> bookReservation(Reservation booking);

    public Flux<Reservation> getReservationByIRoomAndCheckIn(Long idRoom, LocalDateTime checkIn);
}
