package com.rnd.validation.dto;

import com.rnd.validation.dao.entities.ResultatValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultatValidationResponse {
    private Long idResultat;
    private Long idTest;
    private Long valideParId;
    private ResultatValidation.Resultat resultat;
    private String commentaire;
    private String fichierResultat;
    private LocalDateTime dateValidation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



