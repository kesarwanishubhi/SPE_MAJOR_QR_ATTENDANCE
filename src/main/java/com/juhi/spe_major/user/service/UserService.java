package com.juhi.spe_major.user.service;

import com.juhi.spe_major.user.config.JWTUtility;
import com.juhi.spe_major.user.model.LoginRequest;
import com.juhi.spe_major.user.model.UserResponseDTO;
import com.juhi.spe_major.user.model.userRegisterDto;
import com.juhi.spe_major.user.entity.User;
import com.juhi.spe_major.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;
    private JWTUtility jwtUtility;

    @Autowired
    public UserService(UserRepository userRepository, JWTUtility jwtUtility) {
        this.userRepository = userRepository;
        this.jwtUtility = jwtUtility;  // Inject JWTUtility here
    }

    // Register User Method
    public ResponseEntity<UserResponseDTO> registerUser(userRegisterDto dto) {
        // Edge Case 1: If role is STUDENT, imagePath must not be null
        if ("STUDENT".equalsIgnoreCase(dto.getRole()) && (dto.getImagePath() == null || dto.getImagePath().trim().isEmpty())) {
            return ResponseEntity.badRequest().body(new UserResponseDTO(null, null, "Image path is required for STUDENT role"));
        }

        // Check if user with same email and role exists in the database
        User existingUser = userRepository.findByEmailAndRole(dto.getEmail(), dto.getRole());

        if (existingUser != null) {
            return ResponseEntity.badRequest().body(new UserResponseDTO(null, null, "User already exists with this email and role"));
        }


        // Create new user entity
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());
        user.setImagePath(dto.getImagePath());
        user.setPassword("tree"); // default password for all users (unencrypted for now)

        // Save user
        User savedUser = userRepository.save(user);

        // Prepare response DTO
        UserResponseDTO responseDTO = new UserResponseDTO(savedUser.getUserid(), savedUser.getEmail(), savedUser.getPassword());
        return ResponseEntity.ok(responseDTO);
    }

    // Login Method
    public ResponseEntity<?> loginChecking(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password
        if (!request.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtUtility.generateToken(user.getEmail(), user.getRole()); // Get email and role from user

        return ResponseEntity.ok(Map.of(
                "message", "Login Successful",
                "token", token
        ));
    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),  // treat email as "username"
//                user.getPassword(),
//                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
//        );
//    }
}
