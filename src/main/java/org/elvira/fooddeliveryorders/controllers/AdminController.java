package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.model.*;
import org.elvira.fooddeliveryorders.services.OrderService;
import org.elvira.fooddeliveryorders.services.interfaces.IDishService;
import org.elvira.fooddeliveryorders.services.interfaces.IRestaurantService;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/{adminId}")
public class AdminController {

    private static final String REDIRECT_TO_USERS = "redirect:/admin/%s/users";
    private static final String REDIRECT_TO_RESTAURANTS = "redirect:/admin/%s/restaurants";
    private static final String REDIRECT_TO_DISHES = "redirect:/admin/%s/restaurant/%s/dishes";
    private static final String ADMIN_ID = "adminId";
    private static final String RESTAURANT_ATTR_NAME = "restaurant";

    private final IUserService userService;
    private final IRestaurantService restaurantService;
    private final IDishService dishService;
    private final OrderService orderService;

    @Autowired
    public AdminController(IUserService userService,
                           IRestaurantService restaurantService,
                           IDishService dishService, OrderService orderService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.dishService = dishService;
        this.orderService = orderService;
    }

    @ModelAttribute
    public void addAdminIdToModel(@PathVariable(ADMIN_ID) Long adminId, Model model) {
        model.addAttribute(ADMIN_ID, adminId);
    }

    // 1. View all users
    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user-list";
    }

    // 2. Edit user (GET)
    @GetMapping("/user/{id}/edit")
    public String editUserForm(@PathVariable("id") Long id,
                               Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/user-edit";
    }

    // 3. Edit user (POST)
    @PostMapping("/user/{id}/edit")
    public String updateUser(@PathVariable Long id,
                             User updatedUser,
                             Model model) {
        User result = userService.updateUser(updatedUser, id);
        model.addAttribute("user", result);
        return REDIRECT_TO_USERS.formatted(model.getAttribute(ADMIN_ID));
    }

    // 4. Delete user
    @GetMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable Long id,
                             Model model) {
        userService.deleteUser(id);
        return REDIRECT_TO_USERS.formatted(model.getAttribute(ADMIN_ID));
    }

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/add-user")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values()); // To populate roles dropdown
        return "admin/user-add";
    }

    @PostMapping("/add-user")
    public String addUser(@PathVariable(ADMIN_ID) Long adminId,
                          @ModelAttribute("user") User user) {
        userService.registerUser(user);  // Save the new user to the database
        return REDIRECT_TO_USERS.formatted(adminId);
    }

    // Show list of restaurants
    @GetMapping("/restaurants")
    public String viewRestaurants(Model model) {
        Set<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "admin/restaurant-list";
    }

    // Display the create restaurant form
    @GetMapping("/create-restaurant")
    public String showCreateRestaurantForm(Model model) {
        model.addAttribute(RESTAURANT_ATTR_NAME, new Restaurant());
        return "admin/restaurant-add";
    }

    // Handle the form submission for creating a restaurant
    @PostMapping("/create-restaurant")
    public String createRestaurant(@ModelAttribute(RESTAURANT_ATTR_NAME) Restaurant restaurant,
                                   Model model) {
        Restaurant result = restaurantService.createRestaurant(restaurant);
        model.addAttribute(RESTAURANT_ATTR_NAME, result);
        return REDIRECT_TO_RESTAURANTS.formatted(model.getAttribute(ADMIN_ID));
    }

    // Edit restaurant
    @GetMapping("/restaurant/{id}/edit")
    public String editRestaurant(@PathVariable Long id,
                                 Model model) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        model.addAttribute(RESTAURANT_ATTR_NAME, restaurant);
        return "admin/restaurant-edit";
    }

    @PostMapping("/restaurant/{id}/edit")
    public String updateRestaurant(@PathVariable Long id,
                                   @ModelAttribute(RESTAURANT_ATTR_NAME) Restaurant restaurant,
                                   Model model) {
        Restaurant result = restaurantService.updateRestaurant(restaurant, id);
        model.addAttribute(RESTAURANT_ATTR_NAME, result);
        return REDIRECT_TO_RESTAURANTS.formatted(model.getAttribute(ADMIN_ID));
    }

    // Delete restaurant
    @PostMapping("/restaurant/{id}/delete")
    public String deleteRestaurant(@PathVariable Long id,
                                   Model model) {
        restaurantService.deleteRestaurant(id);
        return REDIRECT_TO_RESTAURANTS.formatted(model.getAttribute(ADMIN_ID));
    }

    @GetMapping("/restaurant/{restaurantId}/dishes")
    public String viewRestaurantDishes(@PathVariable("restaurantId") Long restaurantId,
                                       Model model) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        model.addAttribute(RESTAURANT_ATTR_NAME, restaurant);
        model.addAttribute("dishes", restaurant.getDishes());
        return "admin/dish-list";
    }

    // Show form to add a new dish to a restaurant
    @GetMapping("/restaurant/{restaurantId}/add-dish")
    public String showAddDishForm(@PathVariable("restaurantId") Long restaurantId,
                                  Model model) {
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("types", DishType.values());
        model.addAttribute("dish", new Dish());
        return "admin/dish-add";
    }

    // Handle the form submission for adding a new dish
    @PostMapping("/restaurant/{restaurantId}/add-dish")
    public String addDishToRestaurant(@PathVariable("restaurantId") Long restaurantId,
                                      @ModelAttribute("dish") Dish dish,
                                      Model model) {
        Dish result = dishService.createDish(dish, restaurantId);
        model.addAttribute("dish", result);
        return REDIRECT_TO_DISHES.formatted(model.getAttribute(ADMIN_ID), restaurantId);
    }

    // Display form to edit a dish
    @GetMapping("/restaurant/{restaurantId}/dish/{dishId}/edit")
    public String showEditDishForm(@PathVariable("restaurantId") Long restaurantId,
                                   @PathVariable("dishId") Long dishId,
                                   Model model) {
        Dish dish = dishService.getDishById(dishId);  // Retrieves the dish to be edited
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("dish", dish);
        model.addAttribute("dishTypes", DishType.values());
        return "admin/dish-edit";
    }

    // Process the form submission for editing a dish
    @PostMapping("/restaurant/{restaurantId}/dish/{dishId}/edit")
    public String updateDish(@PathVariable("restaurantId") Long restaurantId,
                             @PathVariable("dishId") Long dishId,
                             @ModelAttribute("dish") Dish updatedDish,
                             Model model) {
        Dish result = dishService.updateDish(updatedDish, dishId);  // Updates the dish details
        model.addAttribute("dish", result);
        return REDIRECT_TO_DISHES.formatted(model.getAttribute(ADMIN_ID), restaurantId);
    }

    // Handle request to delete a dish
    @PostMapping("/restaurant/{restaurantId}/dish/{dishId}/delete")
    public String deleteDish(@PathVariable("restaurantId") Long restaurantId,
                             @PathVariable("dishId") Long dishId,
                             Model model) {
        dishService.deleteDish(dishId);  // Deletes the dish
        return REDIRECT_TO_DISHES.formatted(model.getAttribute(ADMIN_ID), restaurantId);
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        List<Order> orders = new ArrayList<>();

        userService.getAllUsers()
                .forEach(user -> orders.addAll(user.getOrders()));

        model.addAttribute("orders", orders);
        return "admin/order-list";
    }

    // Форма для редагування замовлення
    @GetMapping("/order/{id}/edit")
    public String editOrderForm(@PathVariable("id") Long id,
                                Model model) {
        Order order = orderService.getOrder(id);
        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values()); // Список статусів
        return "admin/order-edit";
    }

    // Обробка редагування замовлення
    @PostMapping("/order/{id}/edit")
    public String updateOrder(@PathVariable("id") Long id,
                              @ModelAttribute("order") Order updatedOrder,
                              Model model) {

        orderService.updateOrder(updatedOrder, id);
        return "redirect:/admin/%s/orders".formatted(model.getAttribute(ADMIN_ID));
    }
}
