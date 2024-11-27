package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations.transformers;

import hotel.reservations.domain.model.Reservation;
import hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface ReservationTransformer {

//    @Mapping(target = "checkIn", source = "checkIn", qualifiedByName = "dateFormater")
//    @Mapping(target = "checkOut", source = "checkOut", qualifiedByName = "dateFormater")
    Reservation entityToDomain(ReservationEntity reservation);

    ReservationEntity domainToEntity(Reservation reservation);

//    @Named("dateFormater")
//    default LocalDateTime dateFormater(LocalDateTime dateTime){
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedDate = dateTime.format(dateTimeFormatter);
//        return LocalDateTime.parse(formattedDate);
//    }
}
