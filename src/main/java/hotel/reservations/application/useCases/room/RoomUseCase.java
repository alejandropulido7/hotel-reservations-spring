package hotel.reservations.application.useCases.room;

import hotel.reservations.domain.exceptions.ApplicationException;
import hotel.reservations.domain.gateway.RoomGateway;
import hotel.reservations.domain.model.Room;
import hotel.reservations.domain.model.SearchRooms;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomUseCase {

    private final RoomGateway roomGateway;


    public RoomUseCase(RoomGateway roomGateway) {
        this.roomGateway = roomGateway;
    }

    public Mono<Room> addRoom(Room room){
        System.out.println(room);
        return roomGateway.findRoomsByFloor(room.getFloor())
                .collectList()
                .flatMap(rooms -> {
                    if(rooms.size() == 10){
                        return Mono.error(new ApplicationException("No se puede agregar mas habitaciones al piso "+room.getFloor()));
                    }
                    return roomGateway.addRoom(room);
                });
    }

    public Mono<Void> deleteRoom(Long idRoom){
        return roomGateway.deleteRoom(idRoom)
                .onErrorResume(throwable -> Mono.error(new ApplicationException(throwable.getMessage())));
    }

    public Mono<Void> changeStatus(Long idRoom, boolean status){
        return roomGateway.changeStatusRoom(idRoom, status)
                .flatMap(integer -> {
                    if(integer == 0){
                        return Mono.error(new ApplicationException("No se actualizo ningun registro"));
                    }
                    return Mono.empty();
                })
                .onErrorResume(throwable -> Mono.error(new ApplicationException(throwable.getMessage())))
                .then();
    }

    public Flux<Room> findRoomsAvailables(){
        return roomGateway.findRoomsAvailables()
                .onErrorResume(throwable -> Mono.error(new ApplicationException(throwable.getMessage())));
    }

    public Flux<Room> findRoomsNoAvailables(){
        return roomGateway.findRoomsNoAvailables()
                .onErrorResume(throwable -> Mono.error(new ApplicationException(throwable.getMessage())));
    }

    public Flux<Room> findAllRooms(){
        return roomGateway.findAllRooms()
                .onErrorResume(throwable -> Mono.error(new ApplicationException(throwable.getMessage())));
    }

    public Flux<Room> findAllRoomsByCriteria(SearchRooms searchRooms){
        return roomGateway.findRoomsAvailablesByCriteria(searchRooms)
                .switchIfEmpty(Mono.error(new ApplicationException("Sin habitaciones")))
                .onErrorResume(throwable -> Mono.error(new ApplicationException(throwable.getMessage())));
    }
}
