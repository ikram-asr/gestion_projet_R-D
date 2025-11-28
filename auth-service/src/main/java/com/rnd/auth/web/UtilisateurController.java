package com.rnd.auth.web;

import com.rnd.auth.dto.RegisterRequest;
import com.rnd.auth.dto.UtilisateurResponse;
import com.rnd.auth.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UtilisateurController {
    
    private final UtilisateurService utilisateurService;
    
    @GetMapping("/me")
    public ResponseEntity<UtilisateurResponse> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UtilisateurResponse user = utilisateurService.getUtilisateurByEmail(email);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UtilisateurResponse> getUtilisateurById(@PathVariable Long id) {
        UtilisateurResponse user = utilisateurService.getUtilisateurById(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<UtilisateurResponse>> getAllUtilisateurs() {
        List<UtilisateurResponse> users = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UtilisateurResponse> updateUtilisateur(
            @PathVariable Long id,
            @RequestBody RegisterRequest request) {
        UtilisateurResponse user = utilisateurService.updateUtilisateur(id, request);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}



