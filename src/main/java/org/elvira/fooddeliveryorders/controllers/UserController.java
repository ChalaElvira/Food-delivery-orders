package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user/{id}")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/home")
    public String userHome(@PathVariable("id") Long id, Model model) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/edit-profile")
    public String showEditProfileForm(@PathVariable("id") Long id,
                                      Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@PathVariable("id") Long id,
                                @ModelAttribute("user") User updatedUser) {
        userService.updateUser(updatedUser, id);
        return "redirect:/user/%s/edit-profile?success".formatted(id);
    }

    @PostMapping("/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/login";
    }
}
