package com.alexpyslar03.productselectorbackend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate birth_date;
    private LocalDate registration_date;
    private int access_level;
}
