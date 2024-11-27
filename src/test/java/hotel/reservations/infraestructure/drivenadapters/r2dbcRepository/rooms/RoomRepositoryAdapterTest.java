package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms;

import hotel.reservations.domain.model.Room;
import hotel.reservations.domain.model.SearchRooms;
import hotel.reservations.domain.model.TypeRoom;
import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms.transformers.RoomEntityTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class RoomRepositoryAdapterTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    RoomEntityTransformer roomEntityTransformer;

    @InjectMocks
    RoomRepositoryAdapter roomRepositoryAdapter;

    RoomEntity roomEntity = RoomEntity.builder()
            .location("SUITE")
            .build();

    Room room = Room.builder()
            .location("TERRAZA")
            .build();

    @BeforeEach
    public void setUp() {
        Mockito.when(roomEntityTransformer.domainToEntity(any())).thenReturn(roomEntity);
        Mockito.when(roomEntityTransformer.EntityToDomain(any())).thenReturn(room);
    }

    @Test
    public void addRoom() {
        Mockito.when(roomRepository.save(any())).thenReturn(Mono.just(roomEntity));

        Mono<Room> roomMono = roomRepositoryAdapter.addRoom(room);

        StepVerifier.create(roomMono)
                .consumeNextWith(room1 -> {
                    Assertions.assertEquals(room1.getLocation(), room.getLocation());
                }).verifyComplete();
    }

    @Test
    public void deleteRoom() {
        Mockito.when(roomRepository.deleteById(Mockito.eq(1L))).thenReturn(Mono.empty());

        Mono<Void> roomMono = roomRepositoryAdapter.deleteRoom(1L);

        StepVerifier.create(roomMono).verifyComplete();
    }

    @Test
    public void changeStatusRoom() {
        Mockito.when(roomRepository.changeAvailability(any(), Mockito.any(boolean.class))).thenReturn(Mono.just(1));

        Mono<Integer> roomMono = roomRepositoryAdapter.changeStatusRoom(1L, true);

        StepVerifier.create(roomMono)
                .consumeNextWith(room1 -> {
                    Assertions.assertEquals(room1, 1);
                }).verifyComplete();
    }

    @Test
    public void findRoomById() {
        Mockito.when(roomRepository.findById(Mockito.any(Long.class))).thenReturn(Mono.just(roomEntity));

        Mono<Room> roomMono = roomRepositoryAdapter.findRoomById(1L);

        StepVerifier.create(roomMono)
                .consumeNextWith(room1 -> {
                    Assertions.assertEquals(room1.getLocation(), room.getLocation());
                }).verifyComplete();
    }

    @Test
    public void findRoomsAvailablesByCriteria() {
        SearchRooms searchRooms = SearchRooms.builder()
                .typeRoom(TypeRoom.DOUBLE)
                .amountGuest(1)
                .build();

        Mockito.when(roomRepository.findRoomsAvailablesByCriteria(any(), any(), any(int.class), any())).thenReturn(Flux.just(roomEntity));

        Flux<Room> roomMono = roomRepositoryAdapter.findRoomsAvailablesByCriteria(searchRooms);

        StepVerifier.create(roomMono)
                .consumeNextWith(room1 -> {
                    Assertions.assertEquals(room1.getLocation(), room.getLocation());
                }).verifyComplete();
    }

    @Test
    public void findRoomsAvailables() {
        Mockito.when(roomRepository.roomsByStatusAvailable(true)).thenReturn(Flux.just(roomEntity));

        Flux<Room> roomMono = roomRepositoryAdapter.findRoomsAvailables();

        StepVerifier.create(roomMono)
                .consumeNextWith(room1 -> {
                    Assertions.assertEquals(room1.getLocation(), room.getLocation());
                }).verifyComplete();
    }

    @Test
    public void findRoomsNoAvailables() {
        Mockito.when(roomRepository.roomsByStatusAvailable(false)).thenReturn(Flux.just(roomEntity));

        Flux<Room> roomMono = roomRepositoryAdapter.findRoomsNoAvailables();

        StepVerifier.create(roomMono)
                .consumeNextWith(room1 -> {
                    Assertions.assertEquals(room1.getLocation(), room.getLocation());
                }).verifyComplete();
    }

    @Test
    public void findAllRooms() {
        Mockito.when(roomRepository.findAll()).thenReturn(Flux.just(roomEntity));

        Flux<Room> roomMono = roomRepositoryAdapter.findAllRooms();

        StepVerifier.create(roomMono)
                .consumeNextWith(room1 -> {
                    Assertions.assertEquals(room1.getLocation(), room.getLocation());
                }).verifyComplete();
    }

    @Test
    public void findRoomsByFloor() {
        Mockito.when(roomRepository.findRoomByFloor(1)).thenReturn(Flux.just(roomEntity));

        Flux<Room> roomMono = roomRepositoryAdapter.findRoomsByFloor(1);

        StepVerifier.create(roomMono)
                .consumeNextWith(room1 -> {
                    Assertions.assertEquals(room1.getLocation(), room.getLocation());
                }).verifyComplete();
    }
}