package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.dto.CommentDto;
import server.server.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // POST 요청을 처리하는 메소드
    @PostMapping
    public ResponseEntity<String> saveComment(@RequestBody CommentDto commentDto) {
        try {
            // CommentDTO에서 댓글 내용과 별점을 추출하여 서비스로 전달
            commentService.saveComment(commentDto);
            return ResponseEntity.ok("댓글이 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            // 에러 발생 시 500 Internal Server Error와 함께 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 저장에 실패하였습니다.");
        }
    }

    // CommentDTO 클래스는 댓글 데이터를 전송하기 위한 데이터 클래스로, 필요에 따라 수정하여 사용하시면 됩니다.
    // 예를 들어, CommentDTO 클래스에는 댓글 내용, 별점, 사용자 정보 등의 필드가 있을 수 있습니다.

    // CommentService는 댓글 관련 비즈니스 로직을 처리하는 서비스 클래스로, 필요에 따라 구현하여 사용하시면 됩니다.
    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentsByRestaurantId(@PathVariable("id") Long restaurantId) {
        try {
            // 댓글 데이터를 가져오는 비즈니스 로직 수행
            List<CommentDto> comments = commentService.getCommentsByRestaurantId(restaurantId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            // 에러 발생 시 500 Internal Server Error와 함께 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 조회에 실패하였습니다.");
        }
    }
}