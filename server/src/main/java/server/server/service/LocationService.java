package server.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.dto.PlaceDto;

import java.util.List;

@Service
public class LocationService {
    private final PlaceService placeService;

    public LocationService(PlaceService placeService) {
        this.placeService = placeService;
    }
    public List<PlaceDto> processLocation(double latitude,double longitude){
        String query="restaurant"; //식당인가?
        List<PlaceDto> resturants = placeService.searchPlaces(query, latitude, longitude);
        return resturants;

    }
}
