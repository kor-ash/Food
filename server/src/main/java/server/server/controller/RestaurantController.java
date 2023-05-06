package server.server.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.dto.PlaceDto;
import server.server.dto.RestaurantDto;
import server.server.entity.Restaurant;
import server.server.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/batch")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping
    public ResponseEntity<String> saveRestaurantBatch(@RequestBody List<RestaurantDto> restaurantList) {
        try {
            // 받아온 전체 식당 정보를 개별적으로 서비스를 통해 저장
            for (RestaurantDto restaurantDto : restaurantList) {
                // RestaurantDto를 Restaurant Entity로 변환하여 저장
                Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
                restaurantService.saveRestaurant(restaurant);
            }
            return ResponseEntity.ok().body("식당 정보 저장 완료");
        } catch (Exception e) {
            System.out.println("e = " + e);
            return ResponseEntity.badRequest().body("식당 정보 저장 실패");
        }
    }
    /*
    @GetMapping
    public ResponseEntity<List<Restaurant>> getRestaurantBatch(){
        List<Restaurant> restaurants = restaurantService.findAll();
        if(restaurants==null) {
            System.out.println("hello");
            return ResponseEntity.ok().body(null);
        }
        else
            return ResponseEntity.ok().body(restaurants);
    }
    */
}