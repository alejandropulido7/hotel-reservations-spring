package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations;

import hotel.reservations.domain.gateway.ReservationGateway;
import hotel.reservations.domain.model.Reservation;
import hotel.reservations.domain.model.SearchRooms;
import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations.transformers.ReservationTransformer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class ReservationRespositoryAdapter implements ReservationGateway {

    private final ReservationRepository reservationRepository;
    private final ReservationTransformer reservationTransformer;

    public ReservationRespositoryAdapter(ReservationRepository reservationRepository, ReservationTransformer reservationTransformer) {
        this.reservationRepository = reservationRepository;
        this.reservationTransformer = reservationTransformer;
    }

    @Override
    public Mono<Reservation> bookReservation(Reservation reservation) {
        ReservationEntity reservationEntity = reservationTransformer.domainToEntity(reservation);
        return reservationRepository.save(reservationEntity)
                .map(reservationTransformer::entityToDomain);
    }

    @Override
    public Flux<Reservation> getReservationByIRoomAndCheckIn(Long idRoom, LocalDateTime checkIn) {
        return reservationRepository.findByIdRoomAndCheckIn(idRoom, checkIn)
                .map(reservationTransformer::entityToDomain);
    }

}
