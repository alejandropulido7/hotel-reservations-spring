package hotel.reservations.infraestructure.web.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ReservationDTO {

    @NotNull
    private Long idRoom;
    @FutureOrPresent(message = "CheckIn must not be previous date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkIn;
    @Future(message = "CheckOut must be future date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkOut;
    @Size(max = 3)
    @Pattern(regexp = "C|CE|PA", message = "Id type invalid")
    private String idUserType;
    @Size(max = 12)
    private String idUser;
    @Size(min=2, max=30, message = "Name must have max 30 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name invalid")
    private String nameUser;
    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String emailUser;
}
