package server.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import server.server.dto.PlaceDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PlaceService {
    private final String API_Key;
    private final RestTemplate restTemplate;

    @Autowired
    public PlaceService(@Value("${kakao.map.api.key}") String API_Key, RestTemplate restTemplate) {
        this.API_Key = API_Key;
        this.restTemplate = restTemplate;
    }
    public List<PlaceDto> searchPlaces(String query, double latitude, double longitude) {
        String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + API_Key);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String url = String.format("%s?query=%s&y=%s&x=%s", apiUrl, query, String.valueOf(latitude), String.valueOf(longitude));
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode jsonNode = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            List<PlaceDto> places = objectMapper.convertValue(jsonNode.get("documents"), new TypeReference<List<PlaceDto>>() {
            });
            // 검색 결과를 처리하는 로직
            return places;
        } else {
            // 에러 처리 로직
            return Collections.emptyList();
        }
    }
}
