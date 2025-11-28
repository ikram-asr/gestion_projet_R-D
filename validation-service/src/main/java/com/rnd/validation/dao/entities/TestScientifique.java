package com.rnd.validation.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tests_scientifiques")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestScientifique {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_test")
    private Long idTest;
    
    @Column(name = "id_projet", nullable = false)
    private Long idProjet; // Référence externe au Project-Service
    
    @Column(name = "nom_test", nullable = false)
    private String nomTest;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "type_test")
    private String typeTest;
    
    @Column(name = "responsable_test_id")
    private Long responsableTestId; // Utilisateur (ext) -> responsable_test
    
    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutTest statut = StatutTest.EN_ATTENTE;
    
    @Column(name = "date_debut")
    private LocalDateTime dateDebut;
    
    @Column(name = "date_fin")
    private LocalDateTime dateFin;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "testScientifique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResultatValidation> resultatsValidation;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum StatutTest {
        EN_ATTENTE,
        EN_COURS,
        TERMINE,
        ANNULE
    }
}



