package hotel.reservations.infraestructure.r2dbcRepository.rooms;

import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms.transformers.RoomEntityTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.mock.mockito.MockBean;

class RoomRepositoryAdapterTest {

    @MockBean
    RoomEntityTransformer roomEntityTransformer;

    @BeforeEach
    public void setUp(){
        roomEntityTransformer = Mappers.getMapper(RoomEntityTransformer.class);
    }

    @Test
    void addRoom() {

    }

    @Test
    void deleteRoom() {
    }

    @Test
    void changeStatusRoom() {
    }

    @Test
    void findRoomById() {
    }

    @Test
    void findRoomsAvailablesByDate() {
    }

    @Test
    void findRoomsAvailables() {
    }

    @Test
    void findRoomsNoAvailables() {
    }

    @Test
    void findAllRooms() {
    }
}