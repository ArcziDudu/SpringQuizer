package com.quizer.infrastructure.repository.mapper;

import com.quizer.domain.dto.PlayerDto;
import com.quizer.infrastructure.entity.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlayerMapper {
    PlayerDto mapFromEntity(PlayerEntity player);

    PlayerEntity mapFromDto(PlayerDto adminek);
}
