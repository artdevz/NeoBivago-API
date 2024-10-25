package NeoBivago.entities;

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
public class Room implements Serializable {
    
    // Attributes:

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "hotel")
    private UUID hotel;

    @Column(name = "number")
    private int number;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "price")
    private int price; // Cents

    @Column(name = "type")
    private ERoomType type;    

    // Constructors:
    public Room(UUID hotel, int number, int capacity, int price, ERoomType type) {

        this.hotel = hotel;
        this.number = number;
        this.capacity = capacity;
        this.price = price;
        this.type = type;
        
    }

}
