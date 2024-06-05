package com.fho.digitalpec.api.animalvaccine.mapper;

import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineDTO;
import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.utils.mapper.DataMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalVaccineMapper extends DataMapper<AnimalVaccine, AnimalVaccineDTO> {
}
