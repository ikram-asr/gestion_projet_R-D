package com.rnd.finance.dao.repositories;

import com.rnd.finance.dao.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    
    List<Budget> findByIdProjet(Long idProjet);
    
    List<Budget> findByAnneeBudgetaire(Integer anneeBudgetaire);
    
    List<Budget> findByStatut(Budget.StatutBudget statut);
    
    Optional<Budget> findByIdProjetAndAnneeBudgetaire(Long idProjet, Integer anneeBudgetaire);
}

