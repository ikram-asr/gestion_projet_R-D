package com.rnd.finance.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "depenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Depense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_depense")
    private Long idDepense;
    
    @Column(name = "id_projet", nullable = false)
    private Long idProjet; // Référence externe au Project-Service
    
    @Column(name = "id_budget")
    private Long idBudget; // Référence au budget
    
    @Column(name = "montant", nullable = false)
    private Double montant;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "categorie")
    @Enumerated(EnumType.STRING)
    private CategorieDepense categorie;
    
    @Column(name = "date_depense", nullable = false)
    private LocalDateTime dateDepense;
    
    @Column(name = "approuve_par_id")
    private Long approuveParId; // Utilisateur qui a approuvé
    
    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutDepense statut = StatutDepense.EN_ATTENTE;
    
    @Column(name = "justificatif")
    private String justificatif; // URL ou chemin vers le justificatif
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (dateDepense == null) {
            dateDepense = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum CategorieDepense {
        EQUIPEMENT,
        MATERIEL,
        SALAIRES,
        FORMATION,
        DEPLACEMENT,
        AUTRE
    }
    
    public enum StatutDepense {
        EN_ATTENTE,
        APPROUVE,
        REJETE,
        REMBOURSE
    }
}

