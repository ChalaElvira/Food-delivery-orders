package org.elvira.fooddeliveryorders.repositories;

import org.elvira.fooddeliveryorders.model.Order;
import org.elvira.fooddeliveryorders.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findOrderByUser(User user);
}
