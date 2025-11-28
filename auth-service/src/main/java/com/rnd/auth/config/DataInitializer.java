package com.rnd.auth.config;

import com.rnd.auth.dao.entities.Role;
import com.rnd.auth.dao.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final RoleRepository roleRepository;
    
    @Override
    public void run(String... args) {
        // Initialize default roles if they don't exist
        String[] roles = {"Researcher", "Manager", "Finance", "QA"};
        
        for (String roleName : roles) {
            if (!roleRepository.existsByNomRole(roleName)) {
                Role role = new Role();
                role.setNomRole(roleName);
                roleRepository.save(role);
            }
        }
    }
}



