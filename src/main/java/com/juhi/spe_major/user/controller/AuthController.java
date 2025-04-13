package com.juhi.spe_major.user.controller;

import com.juhi.spe_major.user.model.LoginRequest;
import com.juhi.spe_major.user.entity.User;
import com.juhi.spe_major.user.model.UserResponseDTO;
import com.juhi.spe_major.user.model.userRegisterDto;
import com.juhi.spe_major.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class AuthController {

    //private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthController( UserService userService) {
        //this.authenticationManager = authenticationManager;
        this.userService = userService;
    }
//
//    @Autowired
//    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody userRegisterDto user) {
        // Register the user
        UserResponseDTO registeredUser = userService.registerUser(user).getBody();

        // Return the response
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody  LoginRequest request) {
        return userService.loginChecking(request);
    }

}