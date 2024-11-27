package hotel.reservations.domain.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    private Long idRoom;
    private String idLocation;
    private String location;
    private Double nightCost;
    private int floor;
    private TypeRoom type;
    private int amountGuest;
    private Boolean available;

    public void generateIdLocation (){
        this.idLocation = String.valueOf(this.floor) + "-" + this.location;
    }
}
