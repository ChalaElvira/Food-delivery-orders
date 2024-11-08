package org.elvira.fooddeliveryorders.services.interfaces;

import org.elvira.fooddeliveryorders.model.Dish;

import java.util.Set;

public interface IDishService {
    Dish createDish(Dish dish, Long restaurantId);
    Dish updateDish(Dish source, Long id);
    void deleteDish(Long id);
    Dish getDishById(Long id);
    Set<Dish> getAllDishesByRestaurant(Long restaurantId);
}
