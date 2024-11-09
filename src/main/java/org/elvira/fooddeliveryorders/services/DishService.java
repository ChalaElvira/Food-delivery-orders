package org.elvira.fooddeliveryorders.services;

import org.elvira.fooddeliveryorders.model.Dish;
import org.elvira.fooddeliveryorders.model.Restaurant;
import org.elvira.fooddeliveryorders.repositories.DishRepository;
import org.elvira.fooddeliveryorders.repositories.RestaurantRepository;
import org.elvira.fooddeliveryorders.services.interfaces.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DishService implements IDishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DishService(DishRepository dishRepository,
                       RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Dish createDish(Dish dish, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();

        dish.setRestaurant(restaurant);

        return dishRepository.save(dish);
    }

    @Override
    public Dish updateDish(Dish source, Long id) {
        Dish target = dishRepository.findById(id).orElseThrow();

        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setDishType(source.getDishType());
        target.setPrice(source.getPrice());

        return dishRepository.save(target);
    }

    @Override
    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public Dish getDishById(Long id) {
        return dishRepository.findById(id).orElseThrow();
    }

    @Override
    public Set<Dish> getAllDishesByRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();

        return restaurant.getDishes();
    }
}
