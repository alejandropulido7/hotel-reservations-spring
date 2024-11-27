package hotel.reservations.infraestructure.web.room.transformers;

import hotel.reservations.domain.model.SearchRooms;
import hotel.reservations.domain.model.TypeRoom;
import hotel.reservations.infraestructure.web.room.dto.SearchRoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper
public interface SearchRoomDTOTransformer {

    @Mapping(target = "typeRoom", source = "typeRoom", qualifiedByName = "stringToTypeRoom")
    @Mapping(target = "checkIn", source = "checkIn", qualifiedByName = "dateToLocalDate")
    @Mapping(target = "checkOut", source = "checkOut", qualifiedByName = "dateToLocalDate")
    SearchRooms dtoToDomain (SearchRoomDTO searchRoomDTO);

    SearchRoomDTO domainToDto(SearchRooms searchRooms);

    @Named("stringToTypeRoom")
    default TypeRoom stringToTypeRoom(String typeRoom){
        return TypeRoom.valueOf(typeRoom);
    }

    @Named("dateToLocalDate")
    default LocalDateTime dateToLocalDate(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Named("localDateToDate")
    default Date localDateToDate(LocalDateTime localDate){
        return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
    }
}
