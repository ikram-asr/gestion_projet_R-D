package com.rnd.projects.web;

import com.rnd.projects.dao.entities.Projet;
import com.rnd.projects.dto.ProjetRequest;
import com.rnd.projects.dto.ProjetResponse;
import com.rnd.projects.service.ProjetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjetController {
    
    private final ProjetService projetService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'RESEARCHER')")
    public ResponseEntity<ProjetResponse> createProjet(@Valid @RequestBody ProjetRequest request) {
        ProjetResponse response = projetService.createProjet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProjetResponse> getProjetById(@PathVariable Long id) {
        ProjetResponse response = projetService.getProjetById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<ProjetResponse>> getAllProjets() {
        List<ProjetResponse> projets = projetService.getAllProjets();
        return ResponseEntity.ok(projets);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'RESEARCHER')")
    public ResponseEntity<ProjetResponse> updateProjet(
            @PathVariable Long id,
            @Valid @RequestBody ProjetRequest request) {
        ProjetResponse response = projetService.updateProjet(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/chef/{chefProjetId}")
    public ResponseEntity<List<ProjetResponse>> getProjetsByChefProjet(@PathVariable Long chefProjetId) {
        List<ProjetResponse> projets = projetService.getProjetsByChefProjet(chefProjetId);
        return ResponseEntity.ok(projets);
    }
    
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ProjetResponse>> getProjetsByStatut(@PathVariable Projet.StatutProjet statut) {
        List<ProjetResponse> projets = projetService.getProjetsByStatut(statut);
        return ResponseEntity.ok(projets);
    }
    
    @GetMapping("/departement/{departement}")
    public ResponseEntity<List<ProjetResponse>> getProjetsByDepartement(@PathVariable String departement) {
        List<ProjetResponse> projets = projetService.getProjetsByDepartement(departement);
        return ResponseEntity.ok(projets);
    }
    
    @PutMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('MANAGER', 'RESEARCHER')")
    public ResponseEntity<ProjetResponse> updateStatut(
            @PathVariable Long id,
            @RequestParam Projet.StatutProjet statut) {
        ProjetResponse response = projetService.updateStatut(id, statut);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Projects Service is running");
    }
}

