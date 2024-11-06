package org.elvira.fooddeliveryorders.services;

import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.repositories.UserRepository;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Long id) {
        if (userRepository.findById(id).isPresent()) {
            user.setId(id);
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
    public User deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElseThrow();
            userRepository.delete(user);
        }
        return null;
    }

    @Override
    public Boolean authenticate(String username, String password) {
        User user = userRepository.findUserByUsername(username);

        return user != null && user.getPassword().equals(password);
    }
}
