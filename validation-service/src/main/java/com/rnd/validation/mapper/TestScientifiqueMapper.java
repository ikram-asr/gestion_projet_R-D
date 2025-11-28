package com.rnd.validation.mapper;

import com.rnd.validation.dao.entities.TestScientifique;
import com.rnd.validation.dto.TestScientifiqueRequest;
import com.rnd.validation.dto.TestScientifiqueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestScientifiqueMapper {
    
    @Mapping(target = "idTest", ignore = true)
    @Mapping(target = "statut", constant = "EN_ATTENTE")
    @Mapping(target = "dateDebut", ignore = true)
    @Mapping(target = "dateFin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "resultatsValidation", ignore = true)
    TestScientifique toEntity(TestScientifiqueRequest request);
    
    @Mapping(target = "resultatsValidation", ignore = true)
    TestScientifiqueResponse toResponse(TestScientifique testScientifique);
}

