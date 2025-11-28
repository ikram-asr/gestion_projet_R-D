package com.rnd.validation.service;

import com.rnd.validation.dao.entities.ResultatValidation;
import com.rnd.validation.dao.entities.TestScientifique;
import com.rnd.validation.dao.repositories.ResultatValidationRepository;
import com.rnd.validation.dao.repositories.TestScientifiqueRepository;
import com.rnd.validation.dto.ResultatValidationRequest;
import com.rnd.validation.dto.ResultatValidationResponse;
import com.rnd.validation.dto.TestScientifiqueRequest;
import com.rnd.validation.dto.TestScientifiqueResponse;
import com.rnd.validation.exceptions.ResourceNotFoundException;
import com.rnd.validation.mapper.ResultatValidationMapper;
import com.rnd.validation.mapper.TestScientifiqueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestScientifiqueService {
    
    private final TestScientifiqueRepository testScientifiqueRepository;
    private final ResultatValidationRepository resultatValidationRepository;
    private final TestScientifiqueMapper testScientifiqueMapper;
    private final ResultatValidationMapper resultatValidationMapper;
    
    @Transactional
    public TestScientifiqueResponse createTest(TestScientifiqueRequest request) {
        TestScientifique test = testScientifiqueMapper.toEntity(request);
        test.setDateDebut(LocalDateTime.now());
        test = testScientifiqueRepository.save(test);
        return testScientifiqueMapper.toResponse(test);
    }
    
    public TestScientifiqueResponse getTestById(Long id) {
        TestScientifique test = testScientifiqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + id));
        return testScientifiqueMapper.toResponse(test);
    }
    
    public List<TestScientifiqueResponse> getTestsByProjet(Long idProjet) {
        return testScientifiqueRepository.findByIdProjet(idProjet).stream()
                .map(testScientifiqueMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public List<TestScientifiqueResponse> getTestsByResponsable(Long responsableId) {
        return testScientifiqueRepository.findByResponsableTestId(responsableId).stream()
                .map(testScientifiqueMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public TestScientifiqueResponse updateTestStatus(Long id, TestScientifique.StatutTest statut) {
        TestScientifique test = testScientifiqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + id));
        test.setStatut(statut);
        if (statut == TestScientifique.StatutTest.TERMINE) {
            test.setDateFin(LocalDateTime.now());
        }
        test = testScientifiqueRepository.save(test);
        return testScientifiqueMapper.toResponse(test);
    }
    
    @Transactional
    public ResultatValidationResponse addResultat(ResultatValidationRequest request) {
        TestScientifique test = testScientifiqueRepository.findById(request.getIdTest())
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + request.getIdTest()));
        
        ResultatValidation resultat = resultatValidationMapper.toEntity(request);
        resultat.setTestScientifique(test);
        resultat = resultatValidationRepository.save(resultat);
        
        return resultatValidationMapper.toResponse(resultat);
    }
    
    public List<ResultatValidationResponse> getResultatsByTest(Long idTest) {
        return resultatValidationRepository.findByTestScientifiqueIdTest(idTest).stream()
                .map(resultatValidationMapper::toResponse)
                .collect(Collectors.toList());
    }
}



