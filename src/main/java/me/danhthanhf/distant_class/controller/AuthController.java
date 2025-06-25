package me.danhthanhf.distant_class.controller;

import lombok.RequiredArgsConstructor;
import me.danhthanhf.distant_class.rabbitmq.EmailEventPublisher;
import me.danhthanhf.distant_class.rabbitmq.EmailVerificationEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${api.base.path}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final EmailEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestParam String email) {
        String token = UUID.randomUUID().toString();
        publisher.publishEmailEvent(new EmailVerificationEvent(token, email));
        return ResponseEntity.ok().build();
    }

}
