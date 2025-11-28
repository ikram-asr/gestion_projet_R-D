package com.rnd.projects.dto;

import com.rnd.projects.dao.entities.Projet;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjetResponse {
    
    private Long idProjet;
    private String nomProjet;
    private String description;
    private Long chefProjetId;
    private Projet.StatutProjet statut;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFinPrevue;
    private LocalDateTime dateFinReelle;
    private Double budgetAlloue;
    private String departement;
    private Projet.Priorite priorite;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

