package com.security;

import com.security.entities.User;
import com.security.repositories.UserRepository;
import com.security.security.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Test
    void contextLoads() {
    }

    @Test
    void token() {
        User anish = userRepository.findByUserName("anish").get();
        String token = jwtHelper.generateToken(anish);
        System.out.println(token);
        String usernameFromToken = jwtHelper.getUsernameFromToken(token);
        System.out.println(usernameFromToken);

        Date expirationDateFromToken = jwtHelper.getExpirationDateFromToken(token);
        System.out.println(expirationDateFromToken);

        Boolean tokenExpired = jwtHelper.isTokenExpired(token);
        System.out.println(tokenExpired);
    }

}
