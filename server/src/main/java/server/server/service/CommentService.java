package server.server.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.server.dto.CommentDto;
import server.server.entity.Comment;
import server.server.entity.Restaurant;
import server.server.entity.Users;
import server.server.exception.CommentNotFoundException;
import server.server.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
    public class CommentService {
        @Autowired
        private RestaurantService restaurantService;
        @Autowired
        private UserService userService;
        @Autowired
        private CommentRepository commentRepository;
        @Autowired
        private ModelMapper modelMapper; // ModelMapper를 주입받음 Dto변환
        // 댓글 저장
        public Comment saveComment(CommentDto commentDto) {
            Comment comment = modelMapper.map(commentDto, Comment.class);
            Restaurant restaurant = restaurantService.findByRestaurantName(commentDto.getRestaurantName());
            Users user = userService.findById(commentDto.getUserId());
            comment.setRestaurant(restaurant);
            comment.setUser(user);
            return commentRepository.save(comment);
        }

    // 댓글 조회
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    // 댓글 수정
    @Transactional
    public CommentDto updateComment(Long commentId, String comment, int rating) throws CommentNotFoundException {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if(byId.isPresent()) {
            Comment targetComment = byId.get();
            targetComment.setComment(comment);
            targetComment.setRating(rating);
            commentRepository.save(targetComment);
            return modelMapper.map(targetComment,CommentDto.class);
        }
        else {
            throw new CommentNotFoundException("댓글을 찾을 수 없습니다.");
        }
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<CommentDto> getCommentsByRestaurantId(Long restaurantId) {
        List<Comment> comments = commentRepository.findByRestaurantRestaurantId(restaurantId);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
    public List<CommentDto> findAll(){
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}