package com.fho.digitalpec.api.animal.mapper;

import com.fho.digitalpec.api.animal.dto.AnimalDTO;
import com.fho.digitalpec.api.animal.entity.Animal;
import com.fho.digitalpec.utils.mapper.DataMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalMapper extends DataMapper<Animal, AnimalDTO> {

    default String toPicture(MultipartFile picture) {
        if (picture != null && ObjectUtils.isNotEmpty(picture.getOriginalFilename())) {
            return picture.getOriginalFilename();
        }
        return null;
    }

    default MultipartFile toMultipartFile(String picture) {
        return null;
    }
//
//    default Specie toSpecie(String specie) {
//        if (specie != null) {
//            return Specie.builder()
//                    .name(specie)
//                    .build();
//        }
//        return null;
//    }
//
//    default String toSpecie(Specie specie) {
//        if (specie != null) {
//            return specie.getName();
//        }
//        return null;
//    }
}
