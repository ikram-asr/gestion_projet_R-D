package com.rnd.finance.dto;

import com.rnd.finance.dao.entities.Depense;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepenseRequest {
    
    @NotNull(message = "L'ID du projet est requis")
    private Long idProjet;
    
    private Long idBudget;
    
    @NotNull(message = "Le montant est requis")
    @Positive(message = "Le montant doit être positif")
    private Double montant;
    
    private String description;
    
    private Depense.CategorieDepense categorie;
    
    private LocalDateTime dateDepense;
    
    private Long approuveParId;
    
    private Depense.StatutDepense statut;
    
    private String justificatif;
}

