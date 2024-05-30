package com.fho.digitalpec.api.user.mapper;

import com.fho.digitalpec.api.user.entity.User;
import com.fho.digitalpec.api.user.dto.UserDTO;
import com.fho.digitalpec.utils.mapper.DataMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends DataMapper<User, UserDTO> {
}
