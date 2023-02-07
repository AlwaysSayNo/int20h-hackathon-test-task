package com.hackathon.backend.dto.user;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto {
    private Long id;
    private String login;
}
