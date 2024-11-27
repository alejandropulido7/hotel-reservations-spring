package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms;

import hotel.reservations.domain.model.Room;
import hotel.reservations.domain.model.SearchRooms;
import hotel.reservations.domain.gateway.RoomGateway;
import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms.transformers.RoomEntityTransformer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RoomRepositoryAdapter implements RoomGateway {

    private final RoomRepository roomRepository;
    private final RoomEntityTransformer roomEntityTransformer;

    public RoomRepositoryAdapter(RoomRepository roomRepository, RoomEntityTransformer roomEntityTransformer) {
        this.roomRepository = roomRepository;
        this.roomEntityTransformer = roomEntityTransformer;
    }

    @Override
    public Mono<Room> addRoom(Room room) {
        RoomEntity roomEntity = roomEntityTransformer.domainToEntity(room);
        return roomRepository.save(roomEntity)
                .map(roomEntityTransformer::EntityToDomain);
    }

    @Override
    public Mono<Void> deleteRoom(Long idRoom) {
        return roomRepository.deleteById(idRoom);
    }

    @Override
    public Mono<Integer> changeStatusRoom(Long idRoom, boolean available) {
        return roomRepository.changeAvailability(idRoom, available);
    }

    @Override
    public Mono<Room> findRoomById(Long idRoom) {
        return roomRepository.findById(idRoom)
                .map(roomEntityTransformer::EntityToDomain);
    }

    @Override
    public Flux<Room> findRoomsAvailablesByCriteria(SearchRooms searchRooms) {
        return roomRepository.findRoomsAvailablesByCriteria(
                searchRooms.getCheckIn(), searchRooms.getCheckOut(),
                searchRooms.getAmountGuest(), searchRooms.getTypeRoom().name())
                .map(roomEntityTransformer::EntityToDomain);
    }

    @Override
    public Flux<Room> findRoomsAvailables() {
        return roomRepository.roomsByStatusAvailable(true)
                .map(roomEntityTransformer::EntityToDomain);
    }

    @Override
    public Flux<Room> findRoomsNoAvailables() {
        return roomRepository.roomsByStatusAvailable(false)
                .map(roomEntityTransformer::EntityToDomain);
    }

    @Override
    public Flux<Room> findAllRooms() {
        return roomRepository.findAll()
                .map(roomEntityTransformer::EntityToDomain);
    }

    @Override
    public Flux<Room> findRoomsByFloor(int floor) {
        return roomRepository.findRoomByFloor(floor)
                .map(roomEntityTransformer::EntityToDomain);
    }
}
