package com.rnd.projects.mapper;

import com.rnd.projects.dao.entities.Projet;
import com.rnd.projects.dto.ProjetRequest;
import com.rnd.projects.dto.ProjetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjetMapper {
    
    Projet toEntity(ProjetRequest request);
    
    ProjetResponse toResponse(Projet projet);
    
    void updateEntityFromRequest(ProjetRequest request, @MappingTarget Projet projet);
}

