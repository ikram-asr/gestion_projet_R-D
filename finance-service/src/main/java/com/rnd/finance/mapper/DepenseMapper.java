package com.rnd.finance.mapper;

import com.rnd.finance.dao.entities.Depense;
import com.rnd.finance.dto.DepenseRequest;
import com.rnd.finance.dto.DepenseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepenseMapper {
    
    Depense toEntity(DepenseRequest request);
    
    DepenseResponse toResponse(Depense depense);
    
    void updateEntityFromRequest(DepenseRequest request, @MappingTarget Depense depense);
}

