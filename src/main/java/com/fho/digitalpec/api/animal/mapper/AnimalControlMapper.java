package com.fho.digitalpec.api.animal.mapper;

import com.fho.digitalpec.api.animal.dto.AnimalControlDTO;
import com.fho.digitalpec.api.animal.entity.AnimalControl;
import com.fho.digitalpec.utils.mapper.DataMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalControlMapper extends DataMapper<AnimalControl, AnimalControlDTO> {


    // @Override
    // @Mapping(target = "difference", expression = "java(entity.getAnimalsQuantity() - entity.getTotalExiting())")
    // AnimalControlDTO toDto(AnimalControl entity);
}
