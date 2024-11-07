package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}/home")
    public String userHome(@PathVariable("id") Long id, Model model) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "home";
    }


}
