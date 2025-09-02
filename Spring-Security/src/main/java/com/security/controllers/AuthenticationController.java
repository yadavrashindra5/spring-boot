package com.security.controllers;

import com.security.dtos.JwtRequest;
import com.security.dtos.JwtResponse;
import com.security.entities.User;
import com.security.security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    //method to generate token
    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        String userName = request.getUserName();
        String password = request.getPassword();
        logger.info("username {}, password {}", request.getUserName(), request.getPassword());
        this.doAuthenticate(userName, password);

        UserDetails user = userDetailsService.loadUserByUsername(userName);

        String token = jwtHelper.generateToken(user);
        logger.info("token {}", token);
        JwtResponse build = JwtResponse.builder().token(token).build();
        return ResponseEntity.ok(build);
    }

    private void doAuthenticate(String userName, String password) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userName, password);
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username and password");
        }
    }
}
