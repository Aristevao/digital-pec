package com.fho.digitalpec.api.unit.mapper;

import com.fho.digitalpec.api.unit.dto.UnitDTO;
import com.fho.digitalpec.api.unit.dto.UnitResponseDTO;
import com.fho.digitalpec.api.unit.entity.Unit;
import com.fho.digitalpec.utils.mapper.DataMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper extends DataMapper<Unit, UnitDTO> {

    UnitResponseDTO toResponseDto(Unit entity);

//    default String toPicture(MultipartFile picture) {
//        if (picture != null && ObjectUtils.isNotEmpty(picture.getOriginalFilename())) {
//            return picture.getOriginalFilename();
//        }
//        return null;
//    }
//
//    default MultipartFile toMultipartFile(String picture) {
//        return null;
//    }
}
