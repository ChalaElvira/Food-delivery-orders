package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final IUserService userService;

    private final PasswordEncoder passwordEncoder;  // Для перевірки паролю

    @Autowired
    public LoginController(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String loginForm() {
        return "login";  // Повертає форму логіну
    }

    @PostMapping
    public String performLogin(@RequestParam String username, @RequestParam String password) {
        // Шукаємо користувача за іменем
        User user = userService.getUserByName(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            switch (user.getRole()) {
                case CLIENT, COURIER -> {
                    return "redirect:/user/%s/home".formatted(user.getId());  // Перенаправляємо на домашню сторінку
                }
                case ADMIN -> {
                    return "redirect:/admin/%s/dashboard".formatted(user.getId());
                }
                default -> throw new IllegalStateException("Unexpected value: " + user.getRole());
            }

        }
        return "redirect:/login?error=true";
    }
}
