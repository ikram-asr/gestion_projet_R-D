package com.rnd.auth.service;

import com.rnd.auth.dao.entities.Utilisateur;
import com.rnd.auth.dto.AuthResponse;
import com.rnd.auth.dto.LoginRequest;
import com.rnd.auth.dto.RegisterRequest;
import com.rnd.auth.exceptions.ResourceNotFoundException;
import com.rnd.auth.security.JwtService;
import com.rnd.auth.service.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RabbitTemplate rabbitTemplate;
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Create user
        var utilisateurResponse = utilisateurService.createUtilisateur(request);
        
        // Load user details for JWT
        Utilisateur utilisateur = utilisateurService.loadUserByEmail(request.getEmail());
        UserDetails userDetails = userDetailsService.loadUserByUsername(utilisateur.getEmail());
        
        // Generate JWT
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", utilisateur.getIdUser());
        extraClaims.put("role", utilisateur.getRole().getNomRole());
        String token = jwtService.generateToken(extraClaims, userDetails);
        
        // Publish event
        UserCreatedEvent event = new UserCreatedEvent(
                utilisateur.getIdUser(),
                utilisateur.getEmail(),
                utilisateur.getRole().getNomRole(),
                java.time.LocalDateTime.now().toString()
        );
        rabbitTemplate.convertAndSend("rnd_events", "user.created", event);
        
        return new AuthResponse(
                token,
                utilisateur.getIdUser(),
                utilisateur.getEmail(),
                utilisateur.getPrenom(),
                utilisateur.getNom(),
                utilisateur.getRole().getNomRole()
        );
    }
    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        Utilisateur utilisateur = utilisateurService.loadUserByEmail(request.getEmail());
        
        if (!utilisateur.getIsActive()) {
            throw new ResourceNotFoundException("User account is deactivated");
        }
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(utilisateur.getEmail());
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", utilisateur.getIdUser());
        extraClaims.put("role", utilisateur.getRole().getNomRole());
        String token = jwtService.generateToken(extraClaims, userDetails);
        
        // Publish login event
        Map<String, Object> loginEvent = new HashMap<>();
        loginEvent.put("userId", utilisateur.getIdUser());
        loginEvent.put("email", utilisateur.getEmail());
        loginEvent.put("timestamp", java.time.LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend("rnd_events", "user.logged_in", loginEvent);
        
        return new AuthResponse(
                token,
                utilisateur.getIdUser(),
                utilisateur.getEmail(),
                utilisateur.getPrenom(),
                utilisateur.getNom(),
                utilisateur.getRole().getNomRole()
        );
    }
}
