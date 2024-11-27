package hotel.reservations.application.useCases.room;

import hotel.reservations.domain.exceptions.ApplicationException;
import hotel.reservations.domain.gateway.RoomGateway;
import hotel.reservations.domain.model.Room;
import hotel.reservations.domain.model.SearchRooms;
import hotel.reservations.domain.model.TypeRoom;
import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms.RoomRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class RoomUseCaseTest {

    @Mock
    RoomGateway roomGateway;

    @InjectMocks
    RoomUseCase roomUseCase;

    private Room room1 = Room.builder()
            .location("BALCONY")
            .floor(1)
            .idRoom(1L)
            .build();

    private Room room2 = Room.builder()
            .location("ROOFTOP")
            .floor(2)
            .idRoom(2L)
            .build();

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void addRoomError() {
        List<Room> roomList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            roomList.add(room1);
        }
        Mockito.when(roomGateway.findRoomsByFloor(1)).thenReturn(Flux.fromIterable(roomList));

        Mono<Room> roomMono = roomUseCase.addRoom(room1);

        StepVerifier.create(roomMono).expectError(ApplicationException.class).verify();

    }

    @Test
    public void addRoom() {
        Mockito.when(roomGateway.findRoomsByFloor(1)).thenReturn(Flux.just(room1));
        Mockito.when(roomGateway.addRoom(any())).thenReturn(Mono.just(room2));

        Mono<Room> roomMono = roomUseCase.addRoom(room1);

        StepVerifier.create(roomMono).consumeNextWith(room -> {
            Assertions.assertEquals(room.getIdRoom(), room2.getIdRoom());
            Assertions.assertEquals(room.getFloor(), room2.getFloor());
        }).verifyComplete();
    }

    @Test
    public void deleteRoom() {

        Mockito.when(roomGateway.deleteRoom(1L)).thenReturn(Mono.empty());

        Mono<Void> roomMono = roomUseCase.deleteRoom(1L);

        StepVerifier.create(roomMono).verifyComplete();
    }

    @Test
    public void changeStatus() {
        Mockito.when(roomGateway.changeStatusRoom(1L, true)).thenReturn(Mono.just(1));

        Mono<Void> roomMono = roomUseCase.changeStatus(1L, true);

        StepVerifier.create(roomMono).verifyComplete();
    }

    @Test
    public void changeStatusError() {
        Mockito.when(roomGateway.changeStatusRoom(1L, true)).thenReturn(Mono.just(0));

        Mono<Void> roomMono = roomUseCase.changeStatus(1L, true);

        StepVerifier.create(roomMono).expectError(ApplicationException.class).verify();
    }

    @Test
    public void findRoomsAvailables() {
        List<Room> roomList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            roomList.add(room1);
        }
        Mockito.when(roomGateway.findRoomsAvailables()).thenReturn(Flux.fromIterable(roomList));

        Flux<Room> roomMono = roomUseCase.findRoomsAvailables();

        StepVerifier.create(roomMono).expectNextCount(5).verifyComplete();
    }

    @Test
    public void findRoomsNoAvailables() {
        List<Room> roomList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            roomList.add(room1);
        }
        Mockito.when(roomGateway.findRoomsNoAvailables()).thenReturn(Flux.fromIterable(roomList));

        Flux<Room> roomMono = roomUseCase.findRoomsNoAvailables();

        StepVerifier.create(roomMono).expectNextCount(3).verifyComplete();
    }

    @Test
    public void findAllRooms() {
        List<Room> roomList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            roomList.add(room1);
        }
        Mockito.when(roomGateway.findAllRooms()).thenReturn(Flux.fromIterable(roomList));

        Flux<Room> roomMono = roomUseCase.findAllRooms();

        StepVerifier.create(roomMono).expectNextCount(4).verifyComplete();
    }

    @Test
    public void findAllRoomsByCriteria() {
        List<Room> roomList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            roomList.add(room2);
        }
        SearchRooms searchRooms = SearchRooms.builder()
                .amountGuest(3)
                .typeRoom(TypeRoom.DOUBLE)
                .build();
        Mockito.when(roomGateway.findRoomsAvailablesByCriteria(any())).thenReturn(Flux.fromIterable(roomList));

        Flux<Room> roomMono = roomUseCase.findAllRoomsByCriteria(searchRooms);

        StepVerifier.create(roomMono).expectNext(room2).expectNextCount(5).verifyComplete();
    }

    @Test
    public void findAllRoomsByCriteriaEmpty() {
        SearchRooms searchRooms = SearchRooms.builder()
                .amountGuest(3)
                .typeRoom(TypeRoom.DOUBLE)
                .build();
        Mockito.when(roomGateway.findRoomsAvailablesByCriteria(any())).thenReturn(Flux.empty());

        Flux<Room> roomMono = roomUseCase.findAllRoomsByCriteria(searchRooms);

        StepVerifier.create(roomMono).expectError(ApplicationException.class).verify();
    }
}