package com.rnd.validation.mapper;

import com.rnd.validation.dao.entities.WorkflowValidation;
import com.rnd.validation.dto.WorkflowValidationRequest;
import com.rnd.validation.dto.WorkflowValidationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkflowValidationMapper {
    
    @Mapping(target = "idWorkflow", ignore = true)
    @Mapping(target = "statut", constant = "EN_ATTENTE")
    @Mapping(target = "etapeActuelle", constant = "1")
    @Mapping(target = "dateDebut", ignore = true)
    @Mapping(target = "dateFin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "testsScientifiques", ignore = true)
    WorkflowValidation toEntity(WorkflowValidationRequest request);
    
    WorkflowValidationResponse toResponse(WorkflowValidation workflowValidation);
}



