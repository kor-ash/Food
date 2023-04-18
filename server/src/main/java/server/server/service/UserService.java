package server.server.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.entity.Users;
import server.server.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 정보 저장 메소드
    public void saveUser(Users user) {
        userRepository.save(user);
    }
    public Users findById(Long userId) {
        return userRepository.findById(userId).get();
    }
}
