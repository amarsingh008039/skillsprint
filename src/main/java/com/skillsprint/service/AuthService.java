package com.skillsprint.service;

import com.skillsprint.dto.AuthRequest;
import com.skillsprint.dto.RegisterRequest;
import com.skillsprint.entity.UserEntity;
import com.skillsprint.repository.UserRepository;
import com.skillsprint.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${ADMIN_SECRET}")
    private String adminSecret;

    @Transactional
    public String login(AuthRequest request){
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Credentials"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Credentials");
        }
        return jwtService.generateToken(user.getUsername(),user.getRole());
    }

    @Transactional
    public void register(RegisterRequest request,String requesterRole){
        if (!request.getAdminSecret().equals(adminSecret)) {
            throw new AccessDeniedException("Only admins can create users");
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole().toUpperCase());

        userRepository.save(user);
    }
}
