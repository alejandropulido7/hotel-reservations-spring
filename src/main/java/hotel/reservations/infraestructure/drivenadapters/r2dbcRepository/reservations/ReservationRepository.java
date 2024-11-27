package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends ReactiveCrudRepository<ReservationEntity, Long> {
    @Query("SELECT * FROM reservations WHERE :checkIn BETWEEN check_in AND check_out AND room_id = :idRoom")
    public Flux<ReservationEntity> findByIdRoomAndCheckIn(Long idRoom, LocalDateTime checkIn);
}
