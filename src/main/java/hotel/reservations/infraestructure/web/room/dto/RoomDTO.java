package hotel.reservations.infraestructure.web.room.dto;

import hotel.reservations.domain.model.TypeRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long idRoom;
    private String location;
    private Double nightCost;
    private int floor;
    private String type;
    private int amountGuest;
    private Boolean available;
}
