package com.alexpyslar03.productselectorbackend.dto;

import com.alexpyslar03.productselectorbackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private User.AccessLevel accessLevel;
}