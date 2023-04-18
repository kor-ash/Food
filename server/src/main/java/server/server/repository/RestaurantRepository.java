package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.server.entity.Restaurant;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Restaurant findByRestaurantName(String restaurantName);
}
