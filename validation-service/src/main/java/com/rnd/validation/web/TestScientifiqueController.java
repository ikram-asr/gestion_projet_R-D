package com.rnd.validation.web;

import com.rnd.validation.dao.entities.TestScientifique;
import com.rnd.validation.dto.ResultatValidationRequest;
import com.rnd.validation.dto.ResultatValidationResponse;
import com.rnd.validation.dto.TestScientifiqueRequest;
import com.rnd.validation.dto.TestScientifiqueResponse;
import com.rnd.validation.service.TestScientifiqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validations/tests")
@RequiredArgsConstructor
public class TestScientifiqueController {
    
    private final TestScientifiqueService testScientifiqueService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('RESEARCHER', 'QA', 'MANAGER')")
    public ResponseEntity<TestScientifiqueResponse> createTest(@Valid @RequestBody TestScientifiqueRequest request) {
        TestScientifiqueResponse response = testScientifiqueService.createTest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TestScientifiqueResponse> getTestById(@PathVariable Long id) {
        TestScientifiqueResponse response = testScientifiqueService.getTestById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<TestScientifiqueResponse>> getTestsByProjet(@PathVariable Long idProjet) {
        List<TestScientifiqueResponse> tests = testScientifiqueService.getTestsByProjet(idProjet);
        return ResponseEntity.ok(tests);
    }
    
    @GetMapping("/responsable/{responsableId}")
    public ResponseEntity<List<TestScientifiqueResponse>> getTestsByResponsable(@PathVariable Long responsableId) {
        List<TestScientifiqueResponse> tests = testScientifiqueService.getTestsByResponsable(responsableId);
        return ResponseEntity.ok(tests);
    }
    
    @PutMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('RESEARCHER', 'QA', 'MANAGER')")
    public ResponseEntity<TestScientifiqueResponse> updateTestStatus(
            @PathVariable Long id,
            @RequestParam TestScientifique.StatutTest statut) {
        TestScientifiqueResponse response = testScientifiqueService.updateTestStatus(id, statut);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/resultats")
    @PreAuthorize("hasAnyRole('RESEARCHER', 'QA', 'MANAGER')")
    public ResponseEntity<ResultatValidationResponse> addResultat(
            @Valid @RequestBody ResultatValidationRequest request) {
        ResultatValidationResponse response = testScientifiqueService.addResultat(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}/resultats")
    public ResponseEntity<List<ResultatValidationResponse>> getResultatsByTest(@PathVariable Long id) {
        List<ResultatValidationResponse> resultats = testScientifiqueService.getResultatsByTest(id);
        return ResponseEntity.ok(resultats);
    }
}



