package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.dto.PlaceDto;
import server.server.entity.LocationData;
import server.server.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;
    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    @PostMapping
    public ResponseEntity<List<PlaceDto>> handleLocationData(@RequestBody LocationData locationData) {
        // 받은 위치 정보를 활용하여 원하는 동작 수행
        List<PlaceDto> placeDtos = locationService.processLocation(locationData.getLatitude(), locationData.getLongitude());
        return ResponseEntity.ok().body(placeDtos);
    }
}
