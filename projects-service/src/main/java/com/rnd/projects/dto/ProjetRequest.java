package com.rnd.projects.dto;

import com.rnd.projects.dao.entities.Projet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjetRequest {
    
    @NotBlank(message = "Le nom du projet est requis")
    private String nomProjet;
    
    private String description;
    
    @NotNull(message = "L'ID du chef de projet est requis")
    private Long chefProjetId;
    
    private Projet.StatutProjet statut;
    
    private LocalDateTime dateDebut;
    
    private LocalDateTime dateFinPrevue;
    
    @PositiveOrZero(message = "Le budget doit être positif ou zéro")
    private Double budgetAlloue;
    
    private String departement;
    
    private Projet.Priorite priorite;
}

