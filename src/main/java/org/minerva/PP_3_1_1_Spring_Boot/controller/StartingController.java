package org.minerva.PP_3_1_1_Spring_Boot.controller;

import jakarta.validation.Valid;
import org.minerva.PP_3_1_1_Spring_Boot.model.User;
import org.minerva.PP_3_1_1_Spring_Boot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private UserService userService;
    private final Logger logger;

    public StartingController (UserService userService) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(StartingController.class);
    }

    @GetMapping (value = "/")
    public String showList (Model model) {
        logger.debug("Show list of users");
        model.addAttribute("list_users", userService.getAllUsers());
        return "pages/users_list";
    }

    @GetMapping (value = "/user/edit")
    public String showUser (@RequestParam long id, Model model) {
        logger.debug("Find user with id {}", id);
        if (userService.getUserByID(id) == null) {
            logger.info("User with id {} not found. Redirect to /", id);
            return "redirect:/";
        }
        model.addAttribute("user", userService.getUserByID(id));
        logger.info("Found user with id {}. Show edit form", id);
        return "pages/user";
    }

    @PostMapping ("/")
    public String addUser (@ModelAttribute ("user") @Valid User user, BindingResult bindingResult) {
        logger.debug("Try add user to DB {}", user);
        if (bindingResult.hasErrors()) {
            logger.debug("Validation errors found, User {} not saved", user);
            return "pages/new_user";
        }
        if (userService.existsEmail(user.getEmail())) {
            logger.debug("User with email {} already exists. User {} not saved", user.getEmail(), user);
            bindingResult.addError(new FieldError("user", "email", user.getEmail(), false, null, null,
                    "This e-mail already exists"));
            return "pages/new_user";
        }
        userService.addUser(user);
        logger.info("User saved: {}. Redirect to /", user);
        return "redirect:/";
    }

    @GetMapping (value = "/new")
    public String showNewForm (@ModelAttribute ("user") User user) {
        return "pages/new_user";
    }

    @PostMapping (value = "/update")
    public String updateUser (@RequestParam long id, @ModelAttribute ("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.debug("Validation errors found, User {} not update.Redirect to /user", user);
            return "pages/user";
        }
        User oldUser = userService.getUserByID(id);
        if (oldUser == null) {
            logger.debug("User with id {} not found. Can't update. Redirect to /", id);
            return "redirect:/";
        }
        if (!oldUser.getEmail().equals(user.getEmail()) && userService.existsEmail(user.getEmail())) {
            bindingResult.addError(new FieldError("user", "email", user.getEmail(), false, null, null,
                    "This e-mail already exists"));
            logger.debug("User with email {} already exists. Can't update. Redirect to /user", user.getEmail());
            return "pages/user";
        }
        userService.addUser(user);
        logger.info("User updated: {}. Redirect to /", user);
        return "redirect:/";
    }

    @PostMapping (value = "/delete")
    public String deleteUser (@RequestParam long id) {
        logger.debug("Try delete user with id {}", id);
        if (userService.getUserByID(id) == null) {
            logger.info("User with id {} not found. Can't delete", id);
            return "redirect:/";
        }
        userService.deleteUser(id);
        logger.debug("User with id {} deleted. Redirect to /", id);
        return "redirect:/";
    }
}
