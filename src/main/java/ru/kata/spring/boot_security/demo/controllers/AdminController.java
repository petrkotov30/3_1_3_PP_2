package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, @Lazy RoleServiceImpl roleServiceImpl) {
        this.userService = userServiceImpl;
        this.roleService = roleServiceImpl;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.showAllUser());
        return "users";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "new";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("roles", roleService.findAll());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, @PathVariable("id") Long id, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }
        userService.updateUser(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}