package server.server.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.dto.CommentDto;
import server.server.entity.Comment;
import server.server.entity.Restaurant;
import server.server.entity.Users;
import server.server.repository.CommentRepository;
import server.server.repository.RestaurantRepository;

import java.util.List;
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
    public Comment updateComment(CommentDto commentDto)
    {
        Comment comment = modelMapper.map(commentDto, Comment.class); //commentDto를 Comment로 자동 Mapping
        return commentRepository.save(comment);
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
}