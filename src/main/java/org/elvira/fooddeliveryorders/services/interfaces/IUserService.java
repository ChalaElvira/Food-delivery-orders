package org.elvira.fooddeliveryorders.services.interfaces;

import org.elvira.fooddeliveryorders.model.User;

public interface IUserService {
    User registerUser(User user);
    void updateUser(User user, Long id);
    User getUserById(Long id);
    User getUserByName(String username);
    void deleteUser(Long id);
}
