package org.elvira.fooddeliveryorders.services;

import org.elvira.fooddeliveryorders.model.Role;
import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.repositories.UserRepository;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хешування пароля
        if (user.getRole() == null) {
            user.setRole(Role.CLIENT);
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User source, Long id) {
        User target = userRepository.findById(id).orElseThrow();

        target.setUsername(source.getUsername());
        target.setEmail(source.getEmail());
        target.setPhoneNumber(source.getPhoneNumber());

        if (source.getPassword() != null && !source.getPassword().isEmpty()) {
            target.setPassword(passwordEncoder.encode(source.getPassword()));
        }

        if (source.getRole() != null) {
            target.setRole(source.getRole());
        }

        return userRepository.save(target);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElseThrow();
            userRepository.delete(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
