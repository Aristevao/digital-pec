package com.fho.digitalpec.api.notification.mapper;

import com.fho.digitalpec.api.notification.dto.NotificationDTO;
import com.fho.digitalpec.api.notification.entity.Notification;
import com.fho.digitalpec.utils.mapper.DataMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper extends DataMapper<Notification, NotificationDTO> {
}
