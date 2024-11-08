package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.model.Restaurant;
import org.elvira.fooddeliveryorders.model.Role;
import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.services.RestaurantService;
import org.elvira.fooddeliveryorders.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin/{adminId}")
public class AdminController {

    private static final String REDIRECT_TO_USERS = "redirect:/admin/%s/users";
    private static final String REDIRECT_TO_RESTAURANTS = "redirect:/admin/%s/restaurants";
    private static final String ADMIN_ID = "adminId";

    private final UserService userService;
    private final RestaurantService restaurantService;

    @Autowired
    public AdminController(UserService userService,
                           RestaurantService restaurantService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
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
        return "admin/edit-user";
    }

    // 3. Edit user (POST)
    @PostMapping("/user/{id}/edit")
    public String updateUser(@PathVariable Long id,
                             User updatedUser,
                             Model model) {
        userService.updateUser(updatedUser, id);
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
        return "admin/add-user";
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
        model.addAttribute("restaurant", new Restaurant());
        return "admin/create-restaurant";
    }

    // Handle the form submission for creating a restaurant
    @PostMapping("/create-restaurant")
    public String createRestaurant(@ModelAttribute("restaurant") Restaurant restaurant,
                                   Model model) {
        restaurantService.createRestaurant(restaurant);
        return REDIRECT_TO_RESTAURANTS.formatted(model.getAttribute(ADMIN_ID));
    }

    // Edit restaurant
    @GetMapping("/restaurant/{id}/edit")
    public String editRestaurant(@PathVariable Long id,
                                 Model model) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        model.addAttribute("restaurant", restaurant);
        return "admin/edit-restaurant";
    }

    @PostMapping("/restaurant/{id}/edit")
    public String updateRestaurant(@PathVariable Long id,
                                   @ModelAttribute("restaurant") Restaurant restaurant,
                                   Model model) {
        restaurantService.updateRestaurant(restaurant, id);
        return REDIRECT_TO_RESTAURANTS.formatted(model.getAttribute(ADMIN_ID));
    }

    // Delete restaurant
    @PostMapping("/restaurant/{id}/delete")
    public String deleteRestaurant(@PathVariable Long id,
                                   Model model) {
        restaurantService.deleteRestaurant(id);
        return REDIRECT_TO_RESTAURANTS.formatted(model.getAttribute(ADMIN_ID));
    }
}
