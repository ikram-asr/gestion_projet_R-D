package com.rnd.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkflowValidationRequest {
    
    @NotNull(message = "Project ID is required")
    private Long idProjet;
    
    @NotBlank(message = "Workflow name is required")
    private String nomWorkflow;
    
    private String description;
    
    @NotBlank(message = "Role ID is required")
    private String roleId;
    
    private Integer nombreEtapes;
}



