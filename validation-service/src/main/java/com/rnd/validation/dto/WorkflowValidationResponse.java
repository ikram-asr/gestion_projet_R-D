package com.rnd.validation.dto;

import com.rnd.validation.dao.entities.WorkflowValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowValidationResponse {
    private Long idWorkflow;
    private Long idProjet;
    private String nomWorkflow;
    private String description;
    private String roleId;
    private WorkflowValidation.StatutWorkflow statut;
    private Integer etapeActuelle;
    private Integer nombreEtapes;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



