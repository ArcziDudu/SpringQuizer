package com.quizer.infrastructure.repository.mapper;

import com.quizer.domain.dto.GameDto;
import com.quizer.infrastructure.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameMapper {
    GameEntity mapFromDto(GameDto gameDto);

    GameDto mapFromEntity(GameEntity gameEntity);
}
