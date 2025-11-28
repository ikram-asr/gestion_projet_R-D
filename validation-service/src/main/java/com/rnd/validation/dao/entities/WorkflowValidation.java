package com.rnd.validation.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "workflows_validation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowValidation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_workflow")
    private Long idWorkflow;
    
    @Column(name = "id_projet", nullable = false)
    private Long idProjet; // Référence externe au Project-Service
    
    @Column(name = "nom_workflow", nullable = false)
    private String nomWorkflow;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "role_id", nullable = false)
    private String roleId; // Role (ext) - référence au Auth-Service
    
    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutWorkflow statut = StatutWorkflow.EN_ATTENTE;
    
    @Column(name = "etape_actuelle")
    private Integer etapeActuelle = 1;
    
    @Column(name = "nombre_etapes")
    private Integer nombreEtapes = 1;
    
    @Column(name = "date_debut")
    private LocalDateTime dateDebut;
    
    @Column(name = "date_fin")
    private LocalDateTime dateFin;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "workflowValidation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestScientifique> testsScientifiques;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (dateDebut == null) {
            dateDebut = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum StatutWorkflow {
        EN_ATTENTE,
        EN_COURS,
        VALIDE,
        REJETE,
        ANNULE
    }
}



