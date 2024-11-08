package org.elvira.fooddeliveryorders.repositories;

import org.elvira.fooddeliveryorders.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
