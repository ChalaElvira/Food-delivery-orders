package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/registration")
    public String createUser() {
        return "registration";
    }

    @PostMapping(path = "/registration")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            model.addAttribute("user", userService.createUser(user));
            return "redirect:/user/login";
        } catch (Exception e) {
            model.addAttribute("error", "Помилка під час реєстрації: " + e.getMessage());
            return "registration";
        }
    }

    @GetMapping(path = "/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping(path = "/login")
    public String loginUser(@ModelAttribute("user") User user, Model model) {
        boolean isAuthenticated = userService.authenticate(user.getUsername(), user.getPassword());

        if (isAuthenticated) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Неправильне ім'я користувача або пароль");
            return "login";
        }
    }
}
