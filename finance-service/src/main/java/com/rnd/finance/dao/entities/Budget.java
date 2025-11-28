package com.rnd.finance.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_budget")
    private Long idBudget;
    
    @Column(name = "id_projet", nullable = false)
    private Long idProjet; // Référence externe au Project-Service
    
    @Column(name = "montant_alloue", nullable = false)
    private Double montantAlloue;
    
    @Column(name = "montant_depense")
    private Double montantDepense = 0.0;
    
    @Column(name = "annee_budgetaire", nullable = false)
    private Integer anneeBudgetaire;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutBudget statut = StatutBudget.ACTIF;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum StatutBudget {
        ACTIF,
        CLOTURE,
        ANNULE
    }
}

