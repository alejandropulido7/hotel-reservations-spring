package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms.transformers;

import hotel.reservations.domain.model.Room;
import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms.RoomEntity;
import org.mapstruct.Mapper;

@Mapper
public interface RoomEntityTransformer {

    RoomEntity domainToEntity(Room room);

    Room EntityToDomain(RoomEntity roomEntity);
}
