package org.elvira.fooddeliveryorders.repositories;

import org.elvira.fooddeliveryorders.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
