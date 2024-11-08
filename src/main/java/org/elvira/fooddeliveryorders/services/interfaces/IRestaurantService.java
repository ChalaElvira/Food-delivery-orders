package org.elvira.fooddeliveryorders.services.interfaces;

import org.elvira.fooddeliveryorders.model.Restaurant;

import java.util.Set;

public interface IRestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant updateRestaurant(Restaurant restaurant, Long id);
    Restaurant getRestaurant(Long id);
    Set<Restaurant> getAllRestaurants();
    void deleteRestaurant(Long id);
}
