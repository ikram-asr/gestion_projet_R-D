package com.rnd.finance.dto;

import com.rnd.finance.dao.entities.Budget;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BudgetResponse {
    
    private Long idBudget;
    private Long idProjet;
    private Double montantAlloue;
    private Double montantDepense;
    private Integer anneeBudgetaire;
    private String description;
    private Budget.StatutBudget statut;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

