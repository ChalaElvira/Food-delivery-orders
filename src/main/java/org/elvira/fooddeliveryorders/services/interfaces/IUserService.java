package org.elvira.fooddeliveryorders.services.interfaces;

import org.elvira.fooddeliveryorders.model.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    User updateUser(User user, Long id);
    User getUserById(Long id);
    User getUserByName(String username);
    void deleteUser(Long id);

    List<User> getAllUsers();
}
