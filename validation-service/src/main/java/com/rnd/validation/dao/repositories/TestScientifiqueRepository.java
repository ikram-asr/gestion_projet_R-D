package com.rnd.validation.dao.repositories;

import com.rnd.validation.dao.entities.TestScientifique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestScientifiqueRepository extends JpaRepository<TestScientifique, Long> {
    List<TestScientifique> findByIdProjet(Long idProjet);
    List<TestScientifique> findByResponsableTestId(Long responsableTestId);
    List<TestScientifique> findByStatut(TestScientifique.StatutTest statut);
}



