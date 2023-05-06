package server.server.dto;

import lombok.Data;

@Data
public class CommentDto {
    private String comment;
    private int rating;
    private String restaurantName;
    private Long restaurantId;
    private Long userId;
    private Long commentId;
}
