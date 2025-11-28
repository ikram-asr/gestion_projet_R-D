package com.rnd.finance.dto;

import com.rnd.finance.dao.entities.Depense;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepenseResponse {
    
    private Long idDepense;
    private Long idProjet;
    private Long idBudget;
    private Double montant;
    private String description;
    private Depense.CategorieDepense categorie;
    private LocalDateTime dateDepense;
    private Long approuveParId;
    private Depense.StatutDepense statut;
    private String justificatif;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

