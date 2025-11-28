package com.rnd.validation.dto;

import com.rnd.validation.dao.entities.ResultatValidation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResultatValidationRequest {
    
    @NotNull(message = "Test ID is required")
    private Long idTest;
    
    private Long valideParId;
    
    @NotNull(message = "Result is required")
    private ResultatValidation.Resultat resultat;
    
    private String commentaire;
    
    private String fichierResultat;
}



