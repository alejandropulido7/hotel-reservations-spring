package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface RoomRepository extends ReactiveCrudRepository<RoomEntity, Long> {

    @Modifying
    @Query("UPDATE rooms SET available = :available WHERE id_room = :idRoom")
    Mono<Integer> changeAvailability(Long idRoom, boolean available);

    Flux<RoomEntity> findRoomByFloor(int floor);

    @Query("SELECT * FROM rooms WHERE available = :available")
    Flux<RoomEntity> roomsByStatusAvailable(boolean available);

    @Query("SELECT r.* FROM rooms r " +
            "WHERE 1=1 " +
            "and r.id_room not in (select re.room_id from reservations re where :checkIn BETWEEN re.check_in AND re.check_out OR :checkOut BETWEEN re.check_in AND re.check_out) " +
            "AND :amountGuest <= r.amount_guest " +
            "AND r.type_room = :typeRoom")
    Flux<RoomEntity> findRoomsAvailablesByCriteria(LocalDateTime checkIn, LocalDateTime checkOut, int amountGuest, String typeRoom);
}
