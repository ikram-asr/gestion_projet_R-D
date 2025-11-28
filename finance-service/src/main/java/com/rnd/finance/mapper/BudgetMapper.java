package com.rnd.finance.mapper;

import com.rnd.finance.dao.entities.Budget;
import com.rnd.finance.dto.BudgetRequest;
import com.rnd.finance.dto.BudgetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetMapper {
    
    Budget toEntity(BudgetRequest request);
    
    BudgetResponse toResponse(Budget budget);
    
    void updateEntityFromRequest(BudgetRequest request, @MappingTarget Budget budget);
}

