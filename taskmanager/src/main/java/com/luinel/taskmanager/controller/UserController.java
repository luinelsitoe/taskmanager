package com.luinel.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.luinel.taskmanager.error.DuplicateUserException;
import com.luinel.taskmanager.model.form.UserForm;
import com.luinel.taskmanager.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/login")
  public String loginForm(Model model) {
    var userForm = new UserForm();
    model.addAttribute("userForm", userForm);
    return "login-form";
  }

  @PostMapping("/login/save")
  public String login(Model model, @ModelAttribute UserForm userForm) {
    try {
      var user = userService.login(userForm.getName(), userForm.getPassword());
      return "redirect:/dashboard/" + user.getId();

    } catch (EntityNotFoundException ex) {
      model.addAttribute("errorMessage", ex.getMessage());
      model.addAttribute("userForm", userForm);
      return "login-form";

    } catch (IllegalArgumentException ex) {
      model.addAttribute("errorMessage", ex.getMessage());
      model.addAttribute("userForm", userForm);
      return "login-form";
    }
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    var userForm = new UserForm();
    model.addAttribute("userForm", userForm);
    return "register-form";
  }

  @PostMapping("/register/save")
  public String saveUser(Model model, @ModelAttribute UserForm userForm) {
    try {
      var user = userService.createUser(userForm);
      return "redirect:/dashboard/" + user.getId();

    } catch (DuplicateUserException e) {
      model.addAttribute("errorMessage", e.getMessage());
      model.addAttribute("userForm", userForm);
      return "register-form";
    }
  }

  @GetMapping("/dashboard/{id}")
  public String dashboard(@PathVariable Long id, Model model) {
    var user = userService.findById(id);
    model.addAttribute("user", user);
    return "dashboard";
  }
}
