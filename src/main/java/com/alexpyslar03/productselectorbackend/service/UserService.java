package com.alexpyslar03.productselectorbackend.service;

import com.alexpyslar03.productselectorbackend.dto.UserDTO;
import com.alexpyslar03.productselectorbackend.entity.User;
import com.alexpyslar03.productselectorbackend.exception.UserNotFoundException;
import com.alexpyslar03.productselectorbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

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
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> readAllByIdIn(List<Long> ids) {
        List<User> users = userRepository.findAllByIdIn(ids);
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found for the provided IDs.");
        }
        return users;
    }

    public User update(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException(user.getId());
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}