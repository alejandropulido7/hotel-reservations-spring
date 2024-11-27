package hotel.reservations.infraestructure.web.reservation.transformers;

import hotel.reservations.domain.model.Reservation;
import hotel.reservations.infraestructure.web.reservation.dto.ReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper
public interface ReservationTransformersDTO {

    @Mapping(target = "checkIn", source = "checkIn", qualifiedByName = "dateToLocalDate")
    @Mapping(target = "checkOut", source = "checkOut", qualifiedByName = "dateToLocalDate")
    public Reservation dtoToDomain(ReservationDTO reservationDTO);

    @Mapping(target = "checkIn", source = "checkIn", qualifiedByName = "localDateToDate")
    @Mapping(target = "checkOut", source = "checkOut", qualifiedByName = "localDateToDate")
    public ReservationDTO domainToDto(Reservation reservation);

    @Named("dateToLocalDate")
    default LocalDateTime dateToLocalDate(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Named("localDateToDate")
    default Date localDateToDate(LocalDateTime localDate){
        return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
    }
}
