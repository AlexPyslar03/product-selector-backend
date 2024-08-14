package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(UserDTO dto) {
        return userRepository.save(User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .birthDate(dto.getBirthDate())
                .registrationDate(dto.getRegistrationDate())
                .accessLevel(dto.getAccessLevel())
                .build());
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

    public User readById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found - " + id));
    }

    public List<User> readAllByIdIn(List<Long> ids) {
        return userRepository.findAllByIdIn(ids);
    }

    public User update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found - " + user.getId());
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found - " + id);
        }
        userRepository.deleteById(id);
    }
}
