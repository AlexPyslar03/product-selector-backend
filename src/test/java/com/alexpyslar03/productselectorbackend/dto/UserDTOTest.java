package com.alexpyslar03.productselectorbackend.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDTOTest {

    @Test
    public void testUserDTOSettersAndGetters() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        LocalDate registrationDate = LocalDate.now();

        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");
        userDTO.setBirth_date(birthDate);
        userDTO.setRegistration_date(registrationDate);
        userDTO.setAccess_level(1);

        assertThat(userDTO.getName()).isEqualTo("John");
        assertThat(userDTO.getSurname()).isEqualTo("Doe");
        assertThat(userDTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(userDTO.getPassword()).isEqualTo("password");
        assertThat(userDTO.getBirth_date()).isEqualTo(birthDate);
        assertThat(userDTO.getRegistration_date()).isEqualTo(registrationDate);
        assertThat(userDTO.getAccess_level()).isEqualTo(1);
    }

    @Test
    public void testUserDTOConstructor() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        LocalDate registrationDate = LocalDate.now();

        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");
        userDTO.setBirth_date(birthDate);
        userDTO.setRegistration_date(registrationDate);
        userDTO.setAccess_level(1);

        // Test getter methods directly
        assertThat(userDTO.getName()).isEqualTo("John");
        assertThat(userDTO.getSurname()).isEqualTo("Doe");
        assertThat(userDTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(userDTO.getPassword()).isEqualTo("password");
        assertThat(userDTO.getBirth_date()).isEqualTo(birthDate);
        assertThat(userDTO.getRegistration_date()).isEqualTo(registrationDate);
        assertThat(userDTO.getAccess_level()).isEqualTo(1);
    }
}