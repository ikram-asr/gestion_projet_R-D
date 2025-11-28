package com.rnd.projects.service;

import com.rnd.projects.dao.entities.Projet;
import com.rnd.projects.dao.repositories.ProjetRepository;
import com.rnd.projects.dto.ProjetRequest;
import com.rnd.projects.dto.ProjetResponse;
import com.rnd.projects.exceptions.ResourceNotFoundException;
import com.rnd.projects.mapper.ProjetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjetService {
    
    private final ProjetRepository projetRepository;
    private final ProjetMapper projetMapper;
    
    public ProjetResponse createProjet(ProjetRequest request) {
        Projet projet = projetMapper.toEntity(request);
        if (projet.getStatut() == null) {
            projet.setStatut(Projet.StatutProjet.EN_PLANIFICATION);
        }
        if (projet.getPriorite() == null) {
            projet.setPriorite(Projet.Priorite.MOYENNE);
        }
        Projet savedProjet = projetRepository.save(projet);
        return projetMapper.toResponse(savedProjet);
    }
    
    @Transactional(readOnly = true)
    public ProjetResponse getProjetById(Long id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé avec l'ID: " + id));
        return projetMapper.toResponse(projet);
    }
    
    @Transactional(readOnly = true)
    public List<ProjetResponse> getAllProjets() {
        return projetRepository.findAll().stream()
                .map(projetMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public ProjetResponse updateProjet(Long id, ProjetRequest request) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé avec l'ID: " + id));
        projetMapper.updateEntityFromRequest(request, projet);
        Projet updatedProjet = projetRepository.save(projet);
        return projetMapper.toResponse(updatedProjet);
    }
    
    public void deleteProjet(Long id) {
        if (!projetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Projet non trouvé avec l'ID: " + id);
        }
        projetRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<ProjetResponse> getProjetsByChefProjet(Long chefProjetId) {
        return projetRepository.findByChefProjetId(chefProjetId).stream()
                .map(projetMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProjetResponse> getProjetsByStatut(Projet.StatutProjet statut) {
        return projetRepository.findByStatut(statut).stream()
                .map(projetMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProjetResponse> getProjetsByDepartement(String departement) {
        return projetRepository.findByDepartement(departement).stream()
                .map(projetMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public ProjetResponse updateStatut(Long id, Projet.StatutProjet statut) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé avec l'ID: " + id));
        projet.setStatut(statut);
        if (statut == Projet.StatutProjet.TERMINE) {
            projet.setDateFinReelle(java.time.LocalDateTime.now());
        }
        Projet updatedProjet = projetRepository.save(projet);
        return projetMapper.toResponse(updatedProjet);
    }
}

