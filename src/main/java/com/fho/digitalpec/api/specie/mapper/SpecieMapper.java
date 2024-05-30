package com.fho.digitalpec.api.specie.mapper;

import com.fho.digitalpec.api.specie.dto.SpecieDTO;
import com.fho.digitalpec.api.specie.entity.Specie;
import com.fho.digitalpec.utils.mapper.DataMapper;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecieMapper extends DataMapper<Specie, SpecieDTO> {

    @Override
    @Mapping(target = "description", ignore = true)
    SimpleDTO toSimpleDto(Specie entity);
}
