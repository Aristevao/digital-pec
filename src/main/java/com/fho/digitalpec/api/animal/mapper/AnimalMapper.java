package com.fho.digitalpec.api.animal.mapper;

import com.fho.digitalpec.api.animal.dto.AnimalDTO;
import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.utils.mapper.DataMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalMapper extends DataMapper<Animal, AnimalDTO> {
}
