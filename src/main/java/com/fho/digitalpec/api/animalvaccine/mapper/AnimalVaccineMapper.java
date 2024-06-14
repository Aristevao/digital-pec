package com.fho.digitalpec.api.animalvaccine.mapper;

import java.time.LocalDate;
import java.util.List;

import com.fho.digitalpec.api.animalvaccine.dto.AnimalVaccineDTO;
import com.fho.digitalpec.api.animalvaccine.entity.AnimalVaccine;
import com.fho.digitalpec.api.animalvaccine.entity.NextApplicationDate;
import com.fho.digitalpec.utils.mapper.DataMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalVaccineMapper extends DataMapper<AnimalVaccine, AnimalVaccineDTO> {

    default List<NextApplicationDate> toNextApplicationDateList(List<LocalDate> localDateList) {
        return localDateList.stream().map(localDate -> NextApplicationDate.builder()
                .applicationDate(localDate).build()).toList();
    }

    default List<LocalDate> toLocalDateList(List<NextApplicationDate> nextApplicationDates) {
        return nextApplicationDates.stream().map(NextApplicationDate::getApplicationDate).toList();
    }
}
