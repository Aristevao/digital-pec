package com.fho.digitalpec.api.notification.mapper;

import com.fho.digitalpec.api.notification.dto.NotificationDTO;
import com.fho.digitalpec.api.notification.entity.Notification;
import com.fho.digitalpec.api.user.entity.User;
import com.fho.digitalpec.utils.mapper.DataMapper;
import com.fho.digitalpec.utils.mapper.SimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper extends DataMapper<Notification, NotificationDTO> {

    @Mapping(target = "description", source = "email")
    SimpleDTO toUserDto(User entity);
}
