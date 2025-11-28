package com.rnd.auth.mapper;

import com.rnd.auth.dao.entities.Utilisateur;
import com.rnd.auth.dto.UtilisateurResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    
    @Mapping(source = "idUser", target = "idUser")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "prenom", target = "prenom")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "role.nomRole", target = "roleName")
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    UtilisateurResponse toResponse(Utilisateur utilisateur);
}



