package com.rnd.validation.dto;

import com.rnd.validation.dao.entities.TestScientifique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestScientifiqueResponse {
    private Long idTest;
    private Long idProjet;
    private String nomTest;
    private String description;
    private String typeTest;
    private Long responsableTestId;
    private TestScientifique.StatutTest statut;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ResultatValidationResponse> resultatsValidation;
}



