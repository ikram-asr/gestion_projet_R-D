package com.rnd.finance.dao.repositories;

import com.rnd.finance.dao.entities.Depense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {
    
    List<Depense> findByIdProjet(Long idProjet);
    
    List<Depense> findByIdBudget(Long idBudget);
    
    List<Depense> findByStatut(Depense.StatutDepense statut);
    
    List<Depense> findByCategorie(Depense.CategorieDepense categorie);
}

