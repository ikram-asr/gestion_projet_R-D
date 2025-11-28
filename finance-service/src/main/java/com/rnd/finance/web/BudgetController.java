package com.rnd.finance.web;

import com.rnd.finance.dto.BudgetRequest;
import com.rnd.finance.dto.BudgetResponse;
import com.rnd.finance.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance/budgets")
@RequiredArgsConstructor
public class BudgetController {
    
    private final BudgetService budgetService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'FINANCE')")
    public ResponseEntity<BudgetResponse> createBudget(@Valid @RequestBody BudgetRequest request) {
        BudgetResponse response = budgetService.createBudget(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> getBudgetById(@PathVariable Long id) {
        BudgetResponse response = budgetService.getBudgetById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getAllBudgets() {
        List<BudgetResponse> budgets = budgetService.getAllBudgets();
        return ResponseEntity.ok(budgets);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'FINANCE')")
    public ResponseEntity<BudgetResponse> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequest request) {
        BudgetResponse response = budgetService.updateBudget(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<BudgetResponse>> getBudgetsByProjet(@PathVariable Long idProjet) {
        List<BudgetResponse> budgets = budgetService.getBudgetsByProjet(idProjet);
        return ResponseEntity.ok(budgets);
    }
    
    @GetMapping("/annee/{annee}")
    public ResponseEntity<List<BudgetResponse>> getBudgetsByAnnee(@PathVariable Integer annee) {
        List<BudgetResponse> budgets = budgetService.getBudgetsByAnnee(annee);
        return ResponseEntity.ok(budgets);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Finance Service - Budget is running");
    }
}

