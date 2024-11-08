package org.elvira.fooddeliveryorders.repositories;

import org.elvira.fooddeliveryorders.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {
    List<Dish> findDishByRestaurantId(long restaurantId);
}
