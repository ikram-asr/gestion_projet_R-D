package com.rnd.validation.dao.repositories;

import com.rnd.validation.dao.entities.WorkflowValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowValidationRepository extends JpaRepository<WorkflowValidation, Long> {
    List<WorkflowValidation> findByIdProjet(Long idProjet);
    List<WorkflowValidation> findByRoleId(String roleId);
    List<WorkflowValidation> findByStatut(WorkflowValidation.StatutWorkflow statut);
}



