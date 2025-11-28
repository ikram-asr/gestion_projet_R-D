package com.rnd.finance.web;

import com.rnd.finance.dao.entities.Depense;
import com.rnd.finance.dto.DepenseRequest;
import com.rnd.finance.dto.DepenseResponse;
import com.rnd.finance.service.DepenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance/depenses")
@RequiredArgsConstructor
public class DepenseController {
    
    private final DepenseService depenseService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'RESEARCHER', 'FINANCE')")
    public ResponseEntity<DepenseResponse> createDepense(@Valid @RequestBody DepenseRequest request) {
        DepenseResponse response = depenseService.createDepense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DepenseResponse> getDepenseById(@PathVariable Long id) {
        DepenseResponse response = depenseService.getDepenseById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<DepenseResponse>> getAllDepenses() {
        List<DepenseResponse> depenses = depenseService.getAllDepenses();
        return ResponseEntity.ok(depenses);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'RESEARCHER', 'FINANCE')")
    public ResponseEntity<DepenseResponse> updateDepense(
            @PathVariable Long id,
            @Valid @RequestBody DepenseRequest request) {
        DepenseResponse response = depenseService.updateDepense(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'FINANCE')")
    public ResponseEntity<Void> deleteDepense(@PathVariable Long id) {
        depenseService.deleteDepense(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<DepenseResponse>> getDepensesByProjet(@PathVariable Long idProjet) {
        List<DepenseResponse> depenses = depenseService.getDepensesByProjet(idProjet);
        return ResponseEntity.ok(depenses);
    }
    
    @GetMapping("/budget/{idBudget}")
    public ResponseEntity<List<DepenseResponse>> getDepensesByBudget(@PathVariable Long idBudget) {
        List<DepenseResponse> depenses = depenseService.getDepensesByBudget(idBudget);
        return ResponseEntity.ok(depenses);
    }
    
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<DepenseResponse>> getDepensesByStatut(@PathVariable Depense.StatutDepense statut) {
        List<DepenseResponse> depenses = depenseService.getDepensesByStatut(statut);
        return ResponseEntity.ok(depenses);
    }
    
    @PutMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('MANAGER', 'FINANCE')")
    public ResponseEntity<DepenseResponse> updateStatut(
            @PathVariable Long id,
            @RequestParam Depense.StatutDepense statut,
            @RequestParam(required = false) Long approuveParId) {
        DepenseResponse response = depenseService.updateStatut(id, statut, approuveParId);
        return ResponseEntity.ok(response);
    }
}

