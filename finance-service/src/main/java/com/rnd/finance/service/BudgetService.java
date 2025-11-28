package com.rnd.finance.service;

import com.rnd.finance.dao.entities.Budget;
import com.rnd.finance.dao.repositories.BudgetRepository;
import com.rnd.finance.dto.BudgetRequest;
import com.rnd.finance.dto.BudgetResponse;
import com.rnd.finance.exceptions.ResourceNotFoundException;
import com.rnd.finance.mapper.BudgetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetService {
    
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    
    public BudgetResponse createBudget(BudgetRequest request) {
        Budget budget = budgetMapper.toEntity(request);
        if (budget.getStatut() == null) {
            budget.setStatut(Budget.StatutBudget.ACTIF);
        }
        budget.setMontantDepense(0.0);
        Budget savedBudget = budgetRepository.save(budget);
        return budgetMapper.toResponse(savedBudget);
    }
    
    @Transactional(readOnly = true)
    public BudgetResponse getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget non trouvé avec l'ID: " + id));
        return budgetMapper.toResponse(budget);
    }
    
    @Transactional(readOnly = true)
    public List<BudgetResponse> getAllBudgets() {
        return budgetRepository.findAll().stream()
                .map(budgetMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public BudgetResponse updateBudget(Long id, BudgetRequest request) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget non trouvé avec l'ID: " + id));
        budgetMapper.updateEntityFromRequest(request, budget);
        Budget updatedBudget = budgetRepository.save(budget);
        return budgetMapper.toResponse(updatedBudget);
    }
    
    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget non trouvé avec l'ID: " + id);
        }
        budgetRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<BudgetResponse> getBudgetsByProjet(Long idProjet) {
        return budgetRepository.findByIdProjet(idProjet).stream()
                .map(budgetMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<BudgetResponse> getBudgetsByAnnee(Integer annee) {
        return budgetRepository.findByAnneeBudgetaire(annee).stream()
                .map(budgetMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public BudgetResponse updateMontantDepense(Long id, Double montant) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget non trouvé avec l'ID: " + id));
        budget.setMontantDepense(budget.getMontantDepense() + montant);
        Budget updatedBudget = budgetRepository.save(budget);
        return budgetMapper.toResponse(updatedBudget);
    }
}

