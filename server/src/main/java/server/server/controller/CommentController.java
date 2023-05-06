package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.dto.CommentDto;
import server.server.entity.Comment;
import server.server.exception.CommentNotFoundException;
import server.server.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // POST 요청을 처리하는 메소드
    @PostMapping
    public ResponseEntity<?> saveComment(@RequestBody CommentDto commentDto) {
        try {
            // CommentDTO에서 댓글 내용과 별점을 추출하여 서비스로 전달
            Comment comment=commentService.saveComment(commentDto);
            commentDto.setCommentId(comment.getCommentId());
            return ResponseEntity.ok(commentDto);
        } catch (Exception e) {
            // 에러 발생 시 500 Internal Server Error와 함께 에러 메시지 반환
            System.out.println("e = " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 저장에 실패하였습니다.");
        }
    }
    @PostMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDto comment) {
        try {
            CommentDto updatedCommentDto = commentService.updateComment(commentId, comment.getComment(), comment.getRating());
            return ResponseEntity.ok(updatedCommentDto);
        } catch (Exception e) {
            System.out.println("e = " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정에 실패하였습니다.");
        } catch (CommentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("댓글 삭제에 성공하였습니다.");
        }catch (Exception e){

            System.out.println("e = " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제에 실패하였습니다.");
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
    @GetMapping
    public ResponseEntity<?> getComment(){
        try{
            List<CommentDto> comments = commentService.findAll();
            return ResponseEntity.ok(comments);
        }
        catch (Exception e) {
            // 에러 발생 시 500 Internal Server Error와 함께 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 조회에 실패하였습니다.");
        }
    }
}