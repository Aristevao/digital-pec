package com.fho.digitalpec.api.unit.mapper;

import com.fho.digitalpec.api.unit.dto.UnitDTO;
import com.fho.digitalpec.api.unit.entity.Unit;
import com.fho.digitalpec.utils.mapper.DataMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper extends DataMapper<Unit, UnitDTO> {

    default String toPicture(MultipartFile picture) {
        if (picture != null && ObjectUtils.isNotEmpty(picture.getOriginalFilename())) {
            return picture.getOriginalFilename();
        }
        return null;
    }

    default MultipartFile toMultipartFile(String picture) {
        return null;
    }
}
