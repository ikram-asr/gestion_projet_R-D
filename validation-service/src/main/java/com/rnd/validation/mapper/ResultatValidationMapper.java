package com.rnd.validation.mapper;

import com.rnd.validation.dao.entities.ResultatValidation;
import com.rnd.validation.dto.ResultatValidationRequest;
import com.rnd.validation.dto.ResultatValidationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResultatValidationMapper {
    
    @Mapping(target = "idResultat", ignore = true)
    @Mapping(target = "testScientifique", ignore = true)
    @Mapping(target = "dateValidation", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ResultatValidation toEntity(ResultatValidationRequest request);
    
    @Mapping(source = "testScientifique.idTest", target = "idTest")
    ResultatValidationResponse toResponse(ResultatValidation resultatValidation);
}



