package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user/{userId}")
public class UserController {

    private static final String USER_ID = "userId";
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
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
}
