package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import server.server.dto.PlaceDto;
import server.server.service.PlaceService;

import java.util.List;

@RestController
public class PlaceController {
    @Autowired
    private PlaceService placeService;
    @GetMapping("/api/place/search")
    public ResponseEntity<List<PlaceDto>> searchPlaces(@RequestParam String query,
                                                       @RequestParam double latitude,
                                                       @RequestParam double longitude){
        List<PlaceDto> places=placeService.searchPlaces(query,latitude,longitude);
        return ResponseEntity.ok().body(places);
    }
}
