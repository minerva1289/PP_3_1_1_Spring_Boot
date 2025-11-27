package org.minerva.PP_3_1_1_Spring_Boot.controller;

import jakarta.validation.Valid;
import org.minerva.PP_3_1_1_Spring_Boot.model.User;
import org.minerva.PP_3_1_1_Spring_Boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class StartingController {

    @Autowired
    private UserService userService;

    @GetMapping (value = "/")
    public String showList (Model model) {
        model.addAttribute("list_users", userService.getAllUsers());
        return "pages/users_list";
    }

    @GetMapping (value = "/user/edit")
    public String showUser (@RequestParam long id, Model model) {
        if (userService.getUserByID(id) == null) {
            return "redirect:/";
        }
        model.addAttribute("user", userService.getUserByID(id));
        return "pages/user";
    }

    @PostMapping ("/")
    public String addUser (@ModelAttribute ("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/new_user";
        }
        if (userService.existsEmail(user.getEmail())) {
            bindingResult.addError(new FieldError("user", "email", user.getEmail(), false, null, null,
                    "This e-mail already exists"));
            return "pages/new_user";
        }
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping (value = "/new")
    public String showNewForm (@ModelAttribute ("user") User user) {
        return "pages/new_user";
    }

    @PostMapping (value = "/update")
    public String updateUser (@RequestParam long id, @ModelAttribute ("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/user";
        }
        User oldUser = userService.getUserByID(id);
        if (oldUser == null) {
            return "redirect:/";
        }
        if (!oldUser.getEmail().equals(user.getEmail()) && userService.existsEmail(user.getEmail())) {
            bindingResult.addError(new FieldError("user", "email", user.getEmail(), false, null, null,
                    "This e-mail already exists"));
            return "pages/user";
        }
        userService.updateUser(user);
        return "redirect:/";
    }

    @PostMapping (value = "/delete")
    public String deleteUser (@RequestParam long id) {
        if (userService.getUserByID(id) == null) {
            return "redirect:/";
        }
        userService.deleteUser(id);
        return "redirect:/";
    }
}
