package com.rnd.validation.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "resultats_validation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultatValidation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultat")
    private Long idResultat;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test", nullable = false)
    private TestScientifique testScientifique;
    
    @Column(name = "valide_par_id")
    private Long valideParId; // Utilisateur (ext) -> valide_par
    
    @Column(name = "resultat", nullable = false)
    @Enumerated(EnumType.STRING)
    private Resultat resultat;
    
    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;
    
    @Column(name = "fichier_resultat")
    private String fichierResultat; // Chemin vers le fichier
    
    @Column(name = "date_validation")
    private LocalDateTime dateValidation;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (dateValidation == null && resultat != null) {
            dateValidation = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum Resultat {
        VALIDE,
        NON_VALIDE,
        PARTIELLEMENT_VALIDE,
        EN_ATTENTE
    }
}



