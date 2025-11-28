package com.rnd.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestScientifiqueRequest {
    
    @NotNull(message = "Project ID is required")
    private Long idProjet;
    
    @NotBlank(message = "Test name is required")
    private String nomTest;
    
    private String description;
    
    private String typeTest;
    
    private Long responsableTestId;
}



