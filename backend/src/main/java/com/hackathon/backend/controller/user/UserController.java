package com.hackathon.backend.controller.user;

import com.hackathon.backend.dto.user.UserDto;
import com.hackathon.backend.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/hackathon/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param principal gets from Spring-security
     * @return user dto
     */
    @GetMapping
    public ResponseEntity<?> getUserInfo(Principal principal) {
        try {
            UserDto userInfo = userService.getUserDto(principal.getName());
            return ResponseEntity.ok(userInfo);
        } catch (Throwable t) {
            return ResponseEntity
                    .badRequest()
                    .body(t.getMessage());
        }
    }

}
