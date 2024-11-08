package org.elvira.fooddeliveryorders.services;

import org.elvira.fooddeliveryorders.model.Restaurant;
import org.elvira.fooddeliveryorders.repositories.RestaurantRepository;
import org.elvira.fooddeliveryorders.services.interfaces.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Restaurant source, Long id) {
        Restaurant target = restaurantRepository.findById(id).orElseThrow();

        target.setName(source.getName());
        target.setDescription(source.getDescription());

        return restaurantRepository.save(target);
    }

    @Override
    public Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id).orElseThrow();
    }

    @Override
    public Set<Restaurant> getAllRestaurants() {
        Set<Restaurant> restaurants = new HashSet<>();
        restaurantRepository.findAll().forEach(restaurants::add);
        return restaurants;
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
