package org.elvira.fooddeliveryorders.repositories;

import org.elvira.fooddeliveryorders.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
