package hotel.reservations.infraestructure.web.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import hotel.reservations.application.useCases.room.RoomUseCase;
import hotel.reservations.domain.model.Room;
import hotel.reservations.infraestructure.web.common.ResponseDTO;
import hotel.reservations.infraestructure.web.room.dto.RoomDTO;
import hotel.reservations.infraestructure.web.room.dto.SearchRoomDTO;
import hotel.reservations.infraestructure.web.room.transformers.RoomDTOTransformers;
import hotel.reservations.infraestructure.web.room.transformers.SearchRoomDTOTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = RoomService.class)
@ContextConfiguration(classes = RoomService.class)
public class RoomServiceTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    RoomUseCase useCase;

    @MockBean
    RoomDTOTransformers roomDTOTransformers;

    @MockBean
    SearchRoomDTOTransformer searchRoomDTOTransformer;

    private Room roomMock = Room.builder()
            .location("Terraza")
            .build();

    private RoomDTO roomMockDTO = RoomDTO.builder()
            .location("Terraza")
            .build();

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();
        Mockito.when(useCase.addRoom(any())).thenReturn(Mono.just(roomMock));
        Mockito.when(roomDTOTransformers.domainToDTO(any())).thenReturn(roomMockDTO);
        Mockito.when(roomDTOTransformers.dtoToDomain(any())).thenReturn(roomMock);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addRoom() {


        WebTestClient.ResponseSpec exchange = webClient
                .mutateWith(csrf())
                .post()
                .uri("/room")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(roomMock))
                .exchange();


        exchange.expectStatus().isOk().expectBody(ResponseDTO.class).consumeWith(roomEntityExchangeResult -> {
            RoomDTO roomDTOResonse = objectMapper.convertValue(roomEntityExchangeResult.getResponseBody().getData(), RoomDTO.class);
            Assertions.assertEquals(roomDTOResonse.getLocation(), roomMock.getLocation());
        });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteRoom() {
        Long idRoom = 123L;
        WebTestClient.ResponseSpec exchange = webClient
                .mutateWith(csrf())
                .delete()
                .uri("/room?idRoom={idRoom}", idRoom )
                .exchange();

        exchange.expectStatus().isOk().expectBody(Void.class).returnResult();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void changeStatusRoom() {
        Long idRoom = 123L;
        WebTestClient.ResponseSpec exchange = webClient
                .mutateWith(csrf())
                .put()
                .uri("/room?idRoom={idRoom}&status={status}", idRoom, true )
                .exchange();
        exchange.expectStatus().isOk().expectBody(Void.class).returnResult();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllRooms() {
        Mockito.when(useCase.findAllRooms()).thenReturn(Flux.just(roomMock));
        WebTestClient.ResponseSpec exchange = webClient
                .mutateWith(csrf())
                .get()
                .uri("/room/all")
                .exchange();

        exchange.expectStatus().isOk().expectBodyList(RoomDTO.class)
                .consumeWith(listEntityExchangeResult -> {
                    Assertions.assertEquals(listEntityExchangeResult.getResponseBody().size(), 1);
                    Assertions.assertEquals(listEntityExchangeResult.getResponseBody().get(0).getLocation(), roomMockDTO.getLocation());
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getRoomsAvailables() {
        Mockito.when(useCase.findRoomsAvailables()).thenReturn(Flux.just(roomMock));
        WebTestClient.ResponseSpec exchange = webClient
                .mutateWith(csrf())
                .get()
                .uri("/room/availables")
                .exchange();

        exchange.expectStatus().isOk().expectBodyList(RoomDTO.class)
                .consumeWith(listEntityExchangeResult -> {
                    Assertions.assertEquals(listEntityExchangeResult.getResponseBody().size(), 1);
                    Assertions.assertEquals(listEntityExchangeResult.getResponseBody().get(0).getLocation(), roomMockDTO.getLocation());
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getRoomsNoAvailables() {
        Mockito.when(useCase.findRoomsNoAvailables()).thenReturn(Flux.just(roomMock));
        WebTestClient.ResponseSpec exchange = webClient
                .mutateWith(csrf())
                .get()
                .uri("/room/no-availables")
                .exchange();

        exchange.expectStatus().isOk().expectBodyList(RoomDTO.class)
                .consumeWith(listEntityExchangeResult -> {
                    Assertions.assertEquals(listEntityExchangeResult.getResponseBody().size(), 1);
                    Assertions.assertEquals(listEntityExchangeResult.getResponseBody().get(0).getLocation(), roomMockDTO.getLocation());
                });
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getRoomsByCriteria() {
        Mockito.when(useCase.findAllRoomsByCriteria(any())).thenReturn(Flux.just(roomMock));
        SearchRoomDTO searchRoomDTO = SearchRoomDTO.builder()
                .checkIn(new Date())
                .checkOut(new Date())
                .typeRoom("SUITE")
                .amountGuest(3)
                .build();

        WebTestClient.ResponseSpec exchange = webClient
                .mutateWith(csrf())
                .post()
                .uri("/room/get-by-dates")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(searchRoomDTO))
                .exchange();

        exchange.expectStatus().isOk().expectBodyList(RoomDTO.class)
                .consumeWith(listEntityExchangeResult -> {
                    Assertions.assertEquals(listEntityExchangeResult.getResponseBody().size(), 1);
                    Assertions.assertEquals(listEntityExchangeResult.getResponseBody().get(0).getLocation(), roomMockDTO.getLocation());
                });
    }
}