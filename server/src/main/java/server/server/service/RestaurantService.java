package server.server.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.server.entity.Restaurant;
import server.server.repository.RestaurantRepository;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }

    // 식당 정보 저장 메소드
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }
    public Restaurant findByRestaurantName(String restaurantName) {
        return restaurantRepository.findByRestaurantName(restaurantName);
    }
}
