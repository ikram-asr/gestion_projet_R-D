package com.rnd.finance.dto;

import com.rnd.finance.dao.entities.Budget;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BudgetRequest {
    
    @NotNull(message = "L'ID du projet est requis")
    private Long idProjet;
    
    @NotNull(message = "Le montant alloué est requis")
    @Positive(message = "Le montant doit être positif")
    private Double montantAlloue;
    
    @NotNull(message = "L'année budgétaire est requise")
    private Integer anneeBudgetaire;
    
    private String description;
    
    private Budget.StatutBudget statut;
}

