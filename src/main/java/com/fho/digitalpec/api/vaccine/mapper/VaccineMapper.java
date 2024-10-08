package com.fho.digitalpec.api.vaccine.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fho.digitalpec.api.specie.entity.Specie;
import com.fho.digitalpec.api.vaccine.dto.VaccineDTO;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import com.fho.digitalpec.api.vaccine.entity.VaccineSpecie;
import com.fho.digitalpec.utils.mapper.DataMapper;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VaccineMapper extends DataMapper<Vaccine, VaccineDTO> {

    @Override
    @Mapping(target = "description", ignore = true)
    SimpleDTO toSimpleDto(Vaccine entity);

    default VaccineDTO toDto(Vaccine entity, List<VaccineSpecie> vaccineSpecies) {
        VaccineDTO dto = toDto(entity);
        Map<Long, List<Specie>> speciesMap = mapVaccineSpecies(vaccineSpecies);

        List<Specie> species = speciesMap.getOrDefault(dto.getId(), Collections.emptyList());

        if (dto.getSpecies() == null) {
            dto.setSpecies(new ArrayList<>());
        }

        dto.getSpecies().addAll(species);

        return dto;
    }


    default Page<VaccineDTO> toDto(Page<Vaccine> entities, List<VaccineSpecie> vaccineSpecies) {
        Page<VaccineDTO> dtos = toDto(entities);
        Map<Long, List<Specie>> speciesMap = mapVaccineSpecies(vaccineSpecies);
        addSpeciesToDtos(dtos, speciesMap);
        return dtos;
    }

    default List<VaccineDTO> toDto(List<Vaccine> entities, List<VaccineSpecie> vaccineSpecies) {
        List<VaccineDTO> dtos = toDto(entities);
        Map<Long, List<Specie>> speciesMap = mapVaccineSpecies(vaccineSpecies);
        addSpeciesToDtos(dtos, speciesMap);
        return dtos;
    }

    private Map<Long, List<Specie>> mapVaccineSpecies(List<VaccineSpecie> vaccineSpecies) {
        return vaccineSpecies.stream()
                .peek(vs -> vs.getSpecie().setUser(null))
                .collect(Collectors.groupingBy(
                        vs -> vs.getVaccine().getId(),
                        Collectors.mapping(VaccineSpecie::getSpecie, Collectors.toList())
                ));
    }

    private void addSpeciesToDtos(Iterable<VaccineDTO> dtos, Map<Long, List<Specie>> speciesMap) {
        dtos.forEach(dto -> {
            List<Specie> species = speciesMap.getOrDefault(dto.getId(), Collections.emptyList());
            if (dto.getSpecies() == null) {
                dto.setSpecies(new ArrayList<>());
            }
            dto.getSpecies().addAll(species);
        });
    }
}
