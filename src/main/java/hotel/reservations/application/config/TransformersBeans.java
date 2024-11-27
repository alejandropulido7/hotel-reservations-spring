package hotel.reservations.application.config;

import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations.transformers.ReservationTransformer;
import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms.transformers.RoomEntityTransformer;
import hotel.reservations.infraestructure.web.reservation.transformers.ReservationTransformersDTO;
import hotel.reservations.infraestructure.web.room.transformers.RoomDTOTransformers;
import hotel.reservations.infraestructure.web.room.transformers.SearchRoomDTOTransformer;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransformersBeans {

    @Bean
    public RoomEntityTransformer roomEntityTransformer() {
        return Mappers.getMapper(RoomEntityTransformer.class);
    }

    @Bean
    public RoomDTOTransformers roomDTOTransformers(){
        return Mappers.getMapper(RoomDTOTransformers.class);
    }

    @Bean
    public SearchRoomDTOTransformer searchRoomDTOTransformer(){
        return Mappers.getMapper(SearchRoomDTOTransformer.class);
    }

    @Bean
    public ReservationTransformer reservationTransformer() {
        return Mappers.getMapper(ReservationTransformer.class);
    }

    @Bean
    public ReservationTransformersDTO reservationTransformersDTO() {
        return Mappers.getMapper(ReservationTransformersDTO.class);
    }

}
