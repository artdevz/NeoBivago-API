package NeoBivago.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import NeoBivago.enums.ERoomType;
import NeoBivago.models.RoomModel;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, UUID> {

    RoomModel findByNumber(int number);

    @Query(nativeQuery = true, value = "SELECT * FROM rooms WHERE " +
                                       "(:hotel IS NULL OR hotel = :hotel) AND " +
                                    //    "(:number IS NULL OR number = :number) AND " +
                                       "(:capacity IS NULL OR capacity >= :capacity) AND " +
                                       "(:price IS NULL OR price <= :price) AND " +
                                       "(:type IS NULL OR type = :type)" 
    )
    List<RoomModel> roomFilter(UUID hotel, int capacity, int price, ERoomType type);

} 
