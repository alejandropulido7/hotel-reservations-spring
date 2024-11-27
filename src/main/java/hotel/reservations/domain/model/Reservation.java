package hotel.reservations.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private Long idRoom;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String idUser;
    private String nameUser;
    private String emailUser;
}
