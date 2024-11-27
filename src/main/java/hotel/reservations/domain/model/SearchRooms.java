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
public class SearchRooms {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private TypeRoom typeRoom;
    private Integer amountGuest;
}
