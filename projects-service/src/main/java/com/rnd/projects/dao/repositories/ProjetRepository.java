package com.rnd.projects.dao.repositories;

import com.rnd.projects.dao.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    
    List<Projet> findByChefProjetId(Long chefProjetId);
    
    List<Projet> findByStatut(Projet.StatutProjet statut);
    
    List<Projet> findByDepartement(String departement);
    
    List<Projet> findByPriorite(Projet.Priorite priorite);
    
    Optional<Projet> findByNomProjet(String nomProjet);
}

