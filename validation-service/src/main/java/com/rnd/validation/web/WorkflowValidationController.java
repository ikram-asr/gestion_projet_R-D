package com.rnd.validation.web;

import com.rnd.validation.dao.entities.WorkflowValidation;
import com.rnd.validation.dto.WorkflowValidationRequest;
import com.rnd.validation.dto.WorkflowValidationResponse;
import com.rnd.validation.service.WorkflowValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validations/workflows")
@RequiredArgsConstructor
public class WorkflowValidationController {
    
    private final WorkflowValidationService workflowValidationService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'QA')")
    public ResponseEntity<WorkflowValidationResponse> createWorkflow(
            @Valid @RequestBody WorkflowValidationRequest request) {
        WorkflowValidationResponse response = workflowValidationService.createWorkflow(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<WorkflowValidationResponse> getWorkflowById(@PathVariable Long id) {
        WorkflowValidationResponse response = workflowValidationService.getWorkflowById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<WorkflowValidationResponse>> getWorkflowsByProjet(@PathVariable Long idProjet) {
        List<WorkflowValidationResponse> workflows = workflowValidationService.getWorkflowsByProjet(idProjet);
        return ResponseEntity.ok(workflows);
    }
    
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<WorkflowValidationResponse>> getWorkflowsByRole(@PathVariable String roleId) {
        List<WorkflowValidationResponse> workflows = workflowValidationService.getWorkflowsByRole(roleId);
        return ResponseEntity.ok(workflows);
    }
    
    @PutMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('MANAGER', 'QA')")
    public ResponseEntity<WorkflowValidationResponse> updateWorkflowStatus(
            @PathVariable Long id,
            @RequestParam WorkflowValidation.StatutWorkflow statut) {
        WorkflowValidationResponse response = workflowValidationService.updateWorkflowStatus(id, statut);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/avancer")
    @PreAuthorize("hasAnyRole('MANAGER', 'QA')")
    public ResponseEntity<WorkflowValidationResponse> advanceWorkflow(@PathVariable Long id) {
        WorkflowValidationResponse response = workflowValidationService.advanceWorkflow(id);
        return ResponseEntity.ok(response);
    }
}



