package hotel.reservations.infraestructure.web.room;

import hotel.reservations.application.useCases.room.RoomUseCase;
import hotel.reservations.domain.model.Room;
import hotel.reservations.infraestructure.web.common.ResponseDTO;
import hotel.reservations.infraestructure.web.room.dto.RoomDTO;
import hotel.reservations.infraestructure.web.room.dto.SearchRoomDTO;
import hotel.reservations.infraestructure.web.room.transformers.RoomDTOTransformers;
import hotel.reservations.infraestructure.web.room.transformers.SearchRoomDTOTransformer;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/room")
public class RoomService {

    private final RoomUseCase roomUseCase;
    private final RoomDTOTransformers roomDTOTransformers;
    private final SearchRoomDTOTransformer searchRoomDTOTransformer;

    public RoomService(RoomUseCase roomUseCase, RoomDTOTransformers roomDTOTransformers, SearchRoomDTOTransformer searchRoomDTOTransformer) {
        this.roomUseCase = roomUseCase;
        this.roomDTOTransformers = roomDTOTransformers;
        this.searchRoomDTOTransformer = searchRoomDTOTransformer;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public Mono<ResponseDTO> addRoom(@Valid @RequestBody RoomDTO roomDto){
        return roomUseCase.addRoom(roomDTOTransformers.dtoToDomain(roomDto))
                .map(roomDTOTransformers::domainToDTO)
                .flatMap(roomDTO -> {
                    return Mono.just(ResponseDTO.builder()
                            .data(roomDTO)
                            .status(HttpStatus.OK.value())
                            .build());
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping()
    public Mono<Void> deleteRoom(@Valid @RequestParam Long idRoom) {
        return roomUseCase.deleteRoom(idRoom);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    public Mono<Void> changeStatusRoom(@Valid @RequestParam Long idRoom, @RequestParam boolean status) {
        return roomUseCase.changeStatus(idRoom, status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public Flux<RoomDTO> getAllRooms() {
        return roomUseCase.findAllRooms()
                .map(roomDTOTransformers::domainToDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("availables")
    public Flux<RoomDTO> getRoomsAvailables() {
        return roomUseCase.findRoomsAvailables()
                .map(roomDTOTransformers::domainToDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/no-availables")
    public Flux<RoomDTO> getRoomsNoAvailables() {
        return roomUseCase.findRoomsNoAvailables()
                .map(roomDTOTransformers::domainToDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/get-by-dates")
    public Flux<RoomDTO> getRoomsByCriteria(@RequestBody SearchRoomDTO searchRoomDTO) {
        return roomUseCase.findAllRoomsByCriteria(searchRoomDTOTransformer.dtoToDomain(searchRoomDTO))
                .map(roomDTOTransformers::domainToDTO);
    }
}
