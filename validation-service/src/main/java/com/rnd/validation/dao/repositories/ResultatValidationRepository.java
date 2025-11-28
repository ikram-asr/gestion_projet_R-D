package com.rnd.validation.dao.repositories;

import com.rnd.validation.dao.entities.ResultatValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatValidationRepository extends JpaRepository<ResultatValidation, Long> {
    List<ResultatValidation> findByTestScientifiqueIdTest(Long idTest);
    List<ResultatValidation> findByValideParId(Long valideParId);
    List<ResultatValidation> findByResultat(ResultatValidation.Resultat resultat);
}



