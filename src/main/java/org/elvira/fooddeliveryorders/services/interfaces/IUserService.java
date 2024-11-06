package org.elvira.fooddeliveryorders.services.interfaces;

import org.elvira.fooddeliveryorders.model.User;

public interface IUserService {
    User createUser(User user);
    User updateUser(User user, Long id);
    User getUserById(Long id);
    User deleteUser(Long id);
    Boolean authenticate(String username, String password);
}
