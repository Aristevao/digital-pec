package com.fho.digitalpec.api.user.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String name;

    @NotNull
    @Email
    @Size(max = 320)
    private String email;

    @Size(max = 9)
    private String phone;

    private LocalDate birthdate;

    private String picture;

    private String password;
}
