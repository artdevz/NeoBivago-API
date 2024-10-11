package NeoBivago.models;

import java.io.Serializable;
import java.util.UUID;

import NeoBivago.enums.ERoomType;
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
@Table(name = "rooms")
public class RoomModel implements Serializable {
    
    // Attributes:

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID roomId;

    @Column(name = "hotel")
    private UUID hotelId;

    @Column(name = "number")
    private int roomNumber;

    @Column(name = "capacity")
    private int roomCapacity;

    @Column(name = "price")
    private int roomPrice; // Cents

    @Column(name = "type")
    private ERoomType roomType;    

    // Constructors:
    public RoomModel(UUID hotelId, int roomNumber, int roomCapacity, int roomPrice, ERoomType roomType) {

        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
        
    }

}
