package hotel.reservations.infraestructure.web.room.transformers;

import hotel.reservations.domain.model.Room;
import hotel.reservations.domain.model.TypeRoom;
import hotel.reservations.infraestructure.web.room.dto.RoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface RoomDTOTransformers {

//    @Mapping(target = "type", source = "type", qualifiedByName = "stringToEnumType")
//    @Mapping(target = "idLocation", qualifiedByName = "generateIdLocation")
//    Room dtoToDomain(RoomDTO roomDTO);

    RoomDTO domainToDTO (Room room);

    @Named("stringToEnumType")
    default TypeRoom stringToEnumType(String type){
        return TypeRoom.valueOf(type);
    }

    default Room dtoToDomain(RoomDTO roomDTO){
        Room room = Room.builder()
                .location(roomDTO.getLocation())
                .floor(roomDTO.getFloor())
                .nightCost(roomDTO.getNightCost())
                .amountGuest(roomDTO.getAmountGuest())
                .available(roomDTO.getAvailable())
                .type(TypeRoom.valueOf(roomDTO.getType()))
                .build();
        room.generateIdLocation();
        return room;
    }
}
