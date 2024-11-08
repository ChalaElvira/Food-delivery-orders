package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.model.Role;
import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String REDIRECT_TO_USERS = "redirect:/admin/%s/users";
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // 1. View all users
    @GetMapping("/{id}/users")
    public String viewAllUsers(@PathVariable("id") Long id,
                               Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("id", id);
        return "admin/user-list";
    }

    // 2. Edit user (GET)
    @GetMapping("/{adminId}/user/{id}/edit")
    public String editUserForm(@PathVariable("adminId") Long adminId,
                               @PathVariable("id") Long id,
                               Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("id", adminId);
        return "admin/edit-user";
    }

    // 3. Edit user (POST)
    @PostMapping("/{adminId}/user/{id}/edit")
    public String updateUser(@PathVariable("adminId") Long adminId,
                             @PathVariable Long id,
                             User updatedUser) {
        userService.updateUser(updatedUser, id);
        return REDIRECT_TO_USERS.formatted(adminId);
    }

    // 4. Delete user
    @GetMapping("/{adminId}/user/{id}/delete")
    public String deleteUser(@PathVariable("adminId") Long adminId,
                             @PathVariable Long id) {
        userService.deleteUser(id);
        return REDIRECT_TO_USERS.formatted(adminId);
    }

    @GetMapping("/{id}/dashboard")
    public String adminDashboard(@PathVariable("id") Long id,
                                 Model model) {
        model.addAttribute("userId", id);
        return "admin/dashboard";
    }

    @GetMapping("/{adminId}/add-user")
    public String showAddUserForm(@PathVariable("adminId") Long adminId,
                                  Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values()); // To populate roles dropdown
        model.addAttribute("id", adminId);
        return "admin/add-user";
    }

    @PostMapping("/{adminId}/add-user")
    public String addUser(@PathVariable("adminId") Long adminId,
                          @ModelAttribute("user") User user) {
        userService.registerUser(user);  // Save the new user to the database
        return REDIRECT_TO_USERS.formatted(adminId);
    }
}
