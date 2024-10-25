package NeoBivago.entities;

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
public class Reservation implements Serializable {

    // Attributes:

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID reservationId;

    @Column(name = "user")
    private UUID user;

    @Column(name = "hotel")
    private UUID hotel;

    @Column(name = "room")
    private UUID room;

    @Column(name = "checkIn")
    private Date checkIn;

    @Column(name = "checkOut")
    private Date checkOut;

    @Column(name = "nop") // Number of People
    private int nop;

    @Column(name = "price")
    private int price; // Cents

    // Constructors:
    public Reservation(UUID user, UUID hotel, UUID room, Date checkIn, Date checkOut, int nop, int price) {

        this.user = user;
        this.hotel = hotel;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nop = nop;
        this.price = price;
        
    }
    
}
