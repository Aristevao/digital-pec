package com.fho.digitalpec.utils.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleDTO {

    private Long id;

    private String description;

    private String name;
}

