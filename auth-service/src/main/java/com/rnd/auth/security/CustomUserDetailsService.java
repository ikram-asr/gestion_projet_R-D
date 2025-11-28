package com.rnd.auth.security;

import com.rnd.auth.dao.entities.Utilisateur;
import com.rnd.auth.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UtilisateurService utilisateurService;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurService.loadUserByEmail(email);
        
        if (!utilisateur.getIsActive()) {
            throw new UsernameNotFoundException("User account is deactivated");
        }
        
        return new org.springframework.security.core.userdetails.User(
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                getAuthorities(utilisateur)
        );
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(Utilisateur utilisateur) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().getNomRole()));
    }
}
