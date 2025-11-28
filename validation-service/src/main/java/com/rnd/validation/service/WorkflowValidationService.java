package com.rnd.validation.service;

import com.rnd.validation.dao.entities.WorkflowValidation;
import com.rnd.validation.dao.repositories.WorkflowValidationRepository;
import com.rnd.validation.dto.WorkflowValidationRequest;
import com.rnd.validation.dto.WorkflowValidationResponse;
import com.rnd.validation.exceptions.ResourceNotFoundException;
import com.rnd.validation.mapper.WorkflowValidationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkflowValidationService {
    
    private final WorkflowValidationRepository workflowValidationRepository;
    private final WorkflowValidationMapper workflowValidationMapper;
    
    @Transactional
    public WorkflowValidationResponse createWorkflow(WorkflowValidationRequest request) {
        WorkflowValidation workflow = workflowValidationMapper.toEntity(request);
        if (workflow.getNombreEtapes() == null) {
            workflow.setNombreEtapes(1);
        }
        workflow = workflowValidationRepository.save(workflow);
        return workflowValidationMapper.toResponse(workflow);
    }
    
    public WorkflowValidationResponse getWorkflowById(Long id) {
        WorkflowValidation workflow = workflowValidationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + id));
        return workflowValidationMapper.toResponse(workflow);
    }
    
    public List<WorkflowValidationResponse> getWorkflowsByProjet(Long idProjet) {
        return workflowValidationRepository.findByIdProjet(idProjet).stream()
                .map(workflowValidationMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public List<WorkflowValidationResponse> getWorkflowsByRole(String roleId) {
        return workflowValidationRepository.findByRoleId(roleId).stream()
                .map(workflowValidationMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public WorkflowValidationResponse updateWorkflowStatus(Long id, WorkflowValidation.StatutWorkflow statut) {
        WorkflowValidation workflow = workflowValidationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + id));
        workflow.setStatut(statut);
        if (statut == WorkflowValidation.StatutWorkflow.VALIDE || 
            statut == WorkflowValidation.StatutWorkflow.REJETE) {
            workflow.setDateFin(LocalDateTime.now());
        }
        workflow = workflowValidationRepository.save(workflow);
        return workflowValidationMapper.toResponse(workflow);
    }
    
    @Transactional
    public WorkflowValidationResponse advanceWorkflow(Long id) {
        WorkflowValidation workflow = workflowValidationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + id));
        
        if (workflow.getEtapeActuelle() < workflow.getNombreEtapes()) {
            workflow.setEtapeActuelle(workflow.getEtapeActuelle() + 1);
        } else {
            workflow.setStatut(WorkflowValidation.StatutWorkflow.VALIDE);
            workflow.setDateFin(LocalDateTime.now());
        }
        
        workflow = workflowValidationRepository.save(workflow);
        return workflowValidationMapper.toResponse(workflow);
    }
}



