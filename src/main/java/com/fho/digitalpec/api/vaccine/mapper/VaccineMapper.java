package com.fho.digitalpec.api.vaccine.mapper;

import com.fho.digitalpec.api.vaccine.dto.VaccineDTO;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import com.fho.digitalpec.utils.mapper.DataMapper;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VaccineMapper extends DataMapper<Vaccine, VaccineDTO> {

    @Override
    @Mapping(target = "description", ignore = true)
    SimpleDTO toSimpleDto(Vaccine entity);
}
