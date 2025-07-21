package com.luinel.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import com.luinel.taskmanager.model.form.UserForm;
import com.luinel.taskmanager.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
  private UserService userService;

  @GetMapping("/login")
  public String loginForm(Model model) {
    var userForm = new UserForm();
    model.addAttribute("userForm", userForm);
    return "login-form";
  }

  @GetMapping("/login/save")
  public RedirectView login(@ModelAttribute UserForm userForm) {
    var user = userService.login(userForm.getName(), userForm.getPassword());
    return new RedirectView("/dashboard/" + user.getId());
  }

  @GetMapping("/dashboard/{id}")
  public String dashboard(@PathVariable Long id, Model model) {
    var user = userService.findById(id);
    model.addAttribute("user", user);
    return "dashboard";
  }
}
