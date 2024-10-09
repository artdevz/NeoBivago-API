package NeoBivago.models;

import java.io.Serializable;
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
@Table(name = "hotels")
public class HotelModel implements Serializable {
    
    // Attributes:

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID hotelId;

    @Column(name = "owner_id")
    private UUID hotelOwnerId;

    @Column(name = "name")
    private String hotelName;

    @Column(name = "address")
    private String hotelAddress;

    @Column(name = "city")
    private String hotelCity;

    @Column(name = "score")
    private float hotelScore;

    // Constructors:

    public HotelModel(UUID ownerId, String name, String address, String city, float score) {

        this.hotelOwnerId = ownerId;
        this.hotelName = name;
        this.hotelAddress = address;
        this.hotelCity = city;
        this.hotelScore = score;
        
    }

}
