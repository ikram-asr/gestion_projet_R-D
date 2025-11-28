package com.rnd.finance.service;

import com.rnd.finance.dao.entities.Budget;
import com.rnd.finance.dao.entities.Depense;
import com.rnd.finance.dao.repositories.BudgetRepository;
import com.rnd.finance.dao.repositories.DepenseRepository;
import com.rnd.finance.dto.DepenseRequest;
import com.rnd.finance.dto.DepenseResponse;
import com.rnd.finance.exceptions.ResourceNotFoundException;
import com.rnd.finance.mapper.DepenseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DepenseService {
    
    private final DepenseRepository depenseRepository;
    private final BudgetRepository budgetRepository;
    private final DepenseMapper depenseMapper;
    private final BudgetService budgetService;
    
    public DepenseResponse createDepense(DepenseRequest request) {
        Depense depense = depenseMapper.toEntity(request);
        if (depense.getStatut() == null) {
            depense.setStatut(Depense.StatutDepense.EN_ATTENTE);
        }
        Depense savedDepense = depenseRepository.save(depense);
        
        // Mettre à jour le budget si une dépense est approuvée
        if (savedDepense.getIdBudget() != null && savedDepense.getStatut() == Depense.StatutDepense.APPROUVE) {
            budgetService.updateMontantDepense(savedDepense.getIdBudget(), savedDepense.getMontant());
        }
        
        return depenseMapper.toResponse(savedDepense);
    }
    
    @Transactional(readOnly = true)
    public DepenseResponse getDepenseById(Long id) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépense non trouvée avec l'ID: " + id));
        return depenseMapper.toResponse(depense);
    }
    
    @Transactional(readOnly = true)
    public List<DepenseResponse> getAllDepenses() {
        return depenseRepository.findAll().stream()
                .map(depenseMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public DepenseResponse updateDepense(Long id, DepenseRequest request) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépense non trouvée avec l'ID: " + id));
        
        Double ancienMontant = depense.getMontant();
        Depense.StatutDepense ancienStatut = depense.getStatut();
        
        depenseMapper.updateEntityFromRequest(request, depense);
        Depense updatedDepense = depenseRepository.save(depense);
        
        // Mettre à jour le budget si nécessaire
        if (updatedDepense.getIdBudget() != null) {
            if (ancienStatut == Depense.StatutDepense.APPROUVE && updatedDepense.getStatut() != Depense.StatutDepense.APPROUVE) {
                // Retirer l'ancien montant
                budgetService.updateMontantDepense(updatedDepense.getIdBudget(), -ancienMontant);
            } else if (ancienStatut != Depense.StatutDepense.APPROUVE && updatedDepense.getStatut() == Depense.StatutDepense.APPROUVE) {
                // Ajouter le nouveau montant
                budgetService.updateMontantDepense(updatedDepense.getIdBudget(), updatedDepense.getMontant());
            } else if (ancienStatut == Depense.StatutDepense.APPROUVE && updatedDepense.getStatut() == Depense.StatutDepense.APPROUVE) {
                // Ajuster la différence
                budgetService.updateMontantDepense(updatedDepense.getIdBudget(), updatedDepense.getMontant() - ancienMontant);
            }
        }
        
        return depenseMapper.toResponse(updatedDepense);
    }
    
    public void deleteDepense(Long id) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépense non trouvée avec l'ID: " + id));
        
        // Retirer le montant du budget si la dépense était approuvée
        if (depense.getIdBudget() != null && depense.getStatut() == Depense.StatutDepense.APPROUVE) {
            budgetService.updateMontantDepense(depense.getIdBudget(), -depense.getMontant());
        }
        
        depenseRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<DepenseResponse> getDepensesByProjet(Long idProjet) {
        return depenseRepository.findByIdProjet(idProjet).stream()
                .map(depenseMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<DepenseResponse> getDepensesByBudget(Long idBudget) {
        return depenseRepository.findByIdBudget(idBudget).stream()
                .map(depenseMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<DepenseResponse> getDepensesByStatut(Depense.StatutDepense statut) {
        return depenseRepository.findByStatut(statut).stream()
                .map(depenseMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public DepenseResponse updateStatut(Long id, Depense.StatutDepense statut, Long approuveParId) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépense non trouvée avec l'ID: " + id));
        
        Depense.StatutDepense ancienStatut = depense.getStatut();
        depense.setStatut(statut);
        if (approuveParId != null) {
            depense.setApprouveParId(approuveParId);
        }
        
        Depense updatedDepense = depenseRepository.save(depense);
        
        // Mettre à jour le budget
        if (updatedDepense.getIdBudget() != null) {
            if (ancienStatut == Depense.StatutDepense.APPROUVE && statut != Depense.StatutDepense.APPROUVE) {
                budgetService.updateMontantDepense(updatedDepense.getIdBudget(), -updatedDepense.getMontant());
            } else if (ancienStatut != Depense.StatutDepense.APPROUVE && statut == Depense.StatutDepense.APPROUVE) {
                budgetService.updateMontantDepense(updatedDepense.getIdBudget(), updatedDepense.getMontant());
            }
        }
        
        return depenseMapper.toResponse(updatedDepense);
    }
}

