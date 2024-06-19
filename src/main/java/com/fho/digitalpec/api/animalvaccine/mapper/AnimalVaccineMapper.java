package com.fho.digitalpec.api.animalvaccine.mapper;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineDTO;
import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import com.fho.digitalpec.utils.mapper.DataMapper;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalVaccineMapper extends DataMapper<AnimalVaccine, AnimalVaccineDTO> {

    @Mapping(target = "description", ignore = true)
    SimpleDTO toAnimal(Animal animal);

    @Mapping(target = "description", ignore = true)
    SimpleDTO toVaccine(Vaccine vaccine);

    default List<NextApplicationDate> toNextApplicationDateList(List<LocalDate> localDateList) {
        return localDateList.stream().map(localDate -> NextApplicationDate.builder()
                .applicationDate(localDate).build()).toList();
    }

    default List<LocalDate> toLocalDateList(List<NextApplicationDate> nextApplicationDates) {
        return nextApplicationDates.stream().map(NextApplicationDate::getApplicationDate).toList();
    }
}
