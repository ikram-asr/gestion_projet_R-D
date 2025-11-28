package com.rnd.auth.service;

import com.rnd.auth.dao.entities.Role;
import com.rnd.auth.dao.entities.Utilisateur;
import com.rnd.auth.dao.repositories.RoleRepository;
import com.rnd.auth.dao.repositories.UtilisateurRepository;
import com.rnd.auth.dto.RegisterRequest;
import com.rnd.auth.dto.UtilisateurResponse;
import com.rnd.auth.exceptions.ResourceAlreadyExistsException;
import com.rnd.auth.exceptions.ResourceNotFoundException;
import com.rnd.auth.mapper.UtilisateurMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurService {
    
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurMapper utilisateurMapper;
    
    @Transactional
    public UtilisateurResponse createUtilisateur(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("User already exists with email: " + request.getEmail());
        }
        
        Role role = roleRepository.findByNomRole(request.getRoleName())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + request.getRoleName()));
        
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(request.getPassword()));
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setNom(request.getNom());
        utilisateur.setRole(role);
        utilisateur.setIsActive(true);
        
        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toResponse(utilisateur);
    }
    
    public UtilisateurResponse getUtilisateurById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return utilisateurMapper.toResponse(utilisateur);
    }
    
    public UtilisateurResponse getUtilisateurByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return utilisateurMapper.toResponse(utilisateur);
    }
    
    public List<UtilisateurResponse> getAllUtilisateurs() {
        return utilisateurRepository.findAll().stream()
                .map(utilisateurMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public UtilisateurResponse updateUtilisateur(Long id, RegisterRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        if (request.getEmail() != null && !request.getEmail().equals(utilisateur.getEmail())) {
            if (utilisateurRepository.existsByEmail(request.getEmail())) {
                throw new ResourceAlreadyExistsException("Email already exists: " + request.getEmail());
            }
            utilisateur.setEmail(request.getEmail());
        }
        
        if (request.getPrenom() != null) {
            utilisateur.setPrenom(request.getPrenom());
        }
        
        if (request.getNom() != null) {
            utilisateur.setNom(request.getNom());
        }
        
        if (request.getPassword() != null) {
            utilisateur.setMotDePasse(passwordEncoder.encode(request.getPassword()));
        }
        
        if (request.getRoleName() != null) {
            Role role = roleRepository.findByNomRole(request.getRoleName())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + request.getRoleName()));
            utilisateur.setRole(role);
        }
        
        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toResponse(utilisateur);
    }
    
    @Transactional
    public void deleteUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        utilisateur.setIsActive(false);
        utilisateurRepository.save(utilisateur);
    }
    
    public Utilisateur loadUserByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}



