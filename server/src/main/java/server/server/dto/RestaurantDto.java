package server.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {
    private Long restaurantId;
    private String name;
    private String address;
    private List<CommentDto> comments;
}