package NeoBivago.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class ReservationModel implements Serializable {

    // Attributes:

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID reservationId;

    @Column(name = "user")
    private UUID userId;

    @Column(name = "hotel")
    private UUID hotelid;

    @Column(name = "room")
    private UUID roomId;

    @Column(name = "checkIn")
    private Date reservationCheckIn;

    @Column(name = "checkOut")
    private Date reservationCheckOut;

    @Column(name = "nop") // Number of People
    private int reservationNOP;

    @Column(name = "price")
    private int reservationPrice; // Cents

    // Constructors:
    public ReservationModel(UUID userId, UUID hotelid, UUID roomId, Date reservationCheckIn, Date reservationCheckOut, int reservationNOP, int reservationPrice) {

        this.userId = userId;
        this.hotelid = hotelid;
        this.roomId = roomId;
        this.reservationCheckIn = reservationCheckIn;
        this.reservationCheckOut = reservationCheckOut;
        this.reservationNOP = reservationNOP;
        this.reservationPrice = reservationPrice;
        
    }
    
}
