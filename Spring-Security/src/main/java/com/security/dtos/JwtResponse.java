package com.security.dtos;


import com.security.entities.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    User user;
    private String refreshToken;
}
