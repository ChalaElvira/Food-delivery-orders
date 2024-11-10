package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.components.Cart;
import org.elvira.fooddeliveryorders.model.Dish;
import org.elvira.fooddeliveryorders.model.Restaurant;
import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.services.interfaces.IDishService;
import org.elvira.fooddeliveryorders.services.interfaces.IRestaurantService;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping(path = "/user/{userId}")
public class UserController {

    private static final String USER_ID = "userId";
    private static final String REDIRECT_TO_CART = "redirect:/user/%s/cart";

    private final IUserService userService;
    private final IRestaurantService restaurantService;
    private final IDishService dishService;
    private final Cart cart;

    @Autowired
    public UserController(IUserService userService,
                          IRestaurantService restaurantService,
                          IDishService dishService,
                          Cart cart) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.dishService = dishService;
        this.cart = cart;
    }

    @ModelAttribute
    public void addAttributes(@PathVariable(USER_ID) Long id,
                              Model model) {
        model.addAttribute(USER_ID, id);
    }

    @GetMapping(path = "/home")
    public String userHome(Model model) {
        User user = this.userService.getUserById((Long) model.getAttribute(USER_ID));
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/user-edit")
    public String showEditProfileForm(Model model) {
        User user = userService.getUserById((Long) model.getAttribute(USER_ID));
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/user-edit")
    public String updateProfile(@ModelAttribute("user") User updatedUser,
                                Model model) {
        Long id = (Long) model.getAttribute(USER_ID);
        userService.updateUser(updatedUser, id);
        return "redirect:/user/%s/user-edit?success".formatted(id);
    }

    @PostMapping("/delete")
    public String deleteUser(Model model) {
        userService.deleteUser((Long) model.getAttribute(USER_ID));
        return "redirect:/login";
    }

    // Display all restaurants
    @GetMapping("/restaurants")
    public String viewAllRestaurants(Model model) {
        Set<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "user/restaurant-list";
    }

    // Display dishes for a specific restaurant
    @GetMapping("/restaurant/{restaurantId}/menu")
    public String viewRestaurantMenu(@PathVariable Long restaurantId, Model model) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("dishes", restaurant.getDishes());
        return "user/restaurant-menu";
    }

    // Display details of a specific dish
    @GetMapping("/restaurant/{restaurantId}/dish/{dishId}")
    public String viewDishDetails(@PathVariable Long restaurantId, @PathVariable Long dishId, Model model) {
        Dish dish = dishService.getDishById(dishId);
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("dish", dish);
        return "user/dish-details";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        return "user/cart-view";
    }

    @GetMapping("/dish/{dishId}/add")
    public String addToCart(@PathVariable Long dishId,
                            @RequestParam(defaultValue = "1") int quantity,
                            Model model) {
        Dish dish = dishService.getDishById(dishId);
        cart.addItem(dish, quantity);
        return REDIRECT_TO_CART.formatted(model.getAttribute(USER_ID));
    }

    @GetMapping("/dish/{dishId}/update")
    public String updateQuantity(@PathVariable Long dishId,
                                 @RequestParam int quantity,
                                 Model model) {
        cart.updateItemQuantity(dishId, quantity);
        return REDIRECT_TO_CART.formatted(model.getAttribute(USER_ID));
    }

    @GetMapping("/dish/{dishId}/remove")
    public String removeFromCart(@PathVariable Long dishId,
                                 Model model) {
        cart.removeItem(dishId);
        return REDIRECT_TO_CART.formatted(model.getAttribute(USER_ID));
    }

    @GetMapping("/dish/clear")
    public String clearCart(Model model) {
        cart.clear();
        return REDIRECT_TO_CART.formatted(model.getAttribute(USER_ID));
    }
}
