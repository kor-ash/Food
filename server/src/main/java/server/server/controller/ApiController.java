package server.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/data")
    public ResponseEntity<?> getData() {
        // 데이터를 조회하는 로직 작성
        // 데이터를 응답으로 반환
        return ResponseEntity.ok("Hello World"); // 예시 응답 데이터, 실제 데이터에 따라 수정
    }
}