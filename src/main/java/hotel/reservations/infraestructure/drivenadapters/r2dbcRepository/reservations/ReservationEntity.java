package hotel.reservations.infraestructure.drivenadapters.r2dbcRepository.reservations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("reservations")
public class ReservationEntity {
    @Id
    private Long id;
    @Column("room_id")
    private Long idRoom;
    @Column("check_in")
    private LocalDateTime checkIn;
    @Column("check_out")
    private LocalDateTime checkOut;
    @Column("id_user")
    private String idUser;
    @Column("name_user")
    private String nameUser;
    @Column("email_user")
    private String emailUser;
}
