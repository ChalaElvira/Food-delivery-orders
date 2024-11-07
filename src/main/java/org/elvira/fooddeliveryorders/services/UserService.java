package org.elvira.fooddeliveryorders.services;

import org.elvira.fooddeliveryorders.model.Role;
import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.repositories.UserRepository;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        user.setRole(Role.CLIENT);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Long id) {
        if (userRepository.findById(id).isPresent()) {
            user.setId(id);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).orElseThrow();
        }
        return null;
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElseThrow();
            userRepository.delete(user);
        }
        return null;
    }
}
