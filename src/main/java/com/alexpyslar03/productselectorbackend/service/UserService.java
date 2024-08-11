package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User create(UserDTO dto) {
        return userRepository.save(User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .birth_date(dto.getBirth_date())
                .registration_date(dto.getRegistration_date())
                .registration_date(dto.getRegistration_date())
                .build());
    }
    public List<User> readAll() {
        return userRepository.findAll();
    }
    public User readById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found - " + id));
    }
    public List<User> readByIds(List<Long> ids) {
        return userRepository.findByIds(ids);
    }
    public User update(User user) {
        return userRepository.save(user);
    }
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}