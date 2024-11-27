package hotel.reservations.domain.gateway;

import hotel.reservations.domain.model.Room;
import hotel.reservations.domain.model.SearchRooms;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoomGateway {
    public Mono<Room> addRoom(Room room);

    public Mono<Void> deleteRoom(Long idRoom);

    public Mono<Integer> changeStatusRoom(Long idRoom, boolean available);

    public Mono<Room> findRoomById(Long idRoom);

    public Flux<Room> findRoomsAvailablesByCriteria(SearchRooms searchRooms);

    public Flux<Room> findRoomsAvailables();

    public Flux<Room> findRoomsNoAvailables();

    public Flux<Room> findAllRooms();

    public Flux<Room> findRoomsByFloor(int floor);
}
