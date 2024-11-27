package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.rooms;

import hotel.reservations.domain.model.TypeRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "rooms")
public class RoomEntity {

    @Id
    @Column("id_room")
    private Long idRoom;
    @Column("id_location")
    private String idLocation;
    private String location;
    @Column("night_cost")
    private Double nightCost;
    private int floor;
    @Column("type_room")
    private TypeRoom type;
    @Column("amount_guest")
    private int amountGuest;
    private Boolean available;


}
