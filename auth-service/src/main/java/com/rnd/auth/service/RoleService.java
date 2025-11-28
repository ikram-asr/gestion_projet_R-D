package com.rnd.auth.service;

import com.rnd.auth.dao.entities.Role;
import com.rnd.auth.dao.repositories.RoleRepository;
import com.rnd.auth.exceptions.ResourceAlreadyExistsException;
import com.rnd.auth.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;
    
    @Transactional
    public Role createRole(String nomRole) {
        if (roleRepository.existsByNomRole(nomRole)) {
            throw new ResourceAlreadyExistsException("Role already exists: " + nomRole);
        }
        
        Role role = new Role();
        role.setNomRole(nomRole);
        return roleRepository.save(role);
    }
    
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }
    
    public Role getRoleByNom(String nomRole) {
        return roleRepository.findByNomRole(nomRole)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + nomRole));
    }
    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}



