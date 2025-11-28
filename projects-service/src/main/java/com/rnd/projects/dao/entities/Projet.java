package com.rnd.projects.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "projets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Projet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_projet")
    private Long idProjet;
    
    @Column(name = "nom_projet", nullable = false)
    private String nomProjet;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "chef_projet_id", nullable = false)
    private Long chefProjetId; // Référence externe à l'utilisateur
    
    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutProjet statut = StatutProjet.EN_PLANIFICATION;
    
    @Column(name = "date_debut")
    private LocalDateTime dateDebut;
    
    @Column(name = "date_fin_prevue")
    private LocalDateTime dateFinPrevue;
    
    @Column(name = "date_fin_reelle")
    private LocalDateTime dateFinReelle;
    
    @Column(name = "budget_alloue")
    private Double budgetAlloue;
    
    @Column(name = "departement")
    private String departement;
    
    @Column(name = "priorite")
    @Enumerated(EnumType.STRING)
    private Priorite priorite = Priorite.MOYENNE;
    
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
    
    public enum StatutProjet {
        EN_PLANIFICATION,
        EN_COURS,
        EN_PAUSE,
        TERMINE,
        ANNULE
    }
    
    public enum Priorite {
        BASSE,
        MOYENNE,
        HAUTE,
        CRITIQUE
    }
}

