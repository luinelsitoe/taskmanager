package com.luinel.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.luinel.taskmanager.model.form.UserForm;
import com.luinel.taskmanager.service.TaskService;
import com.luinel.taskmanager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final TaskService taskService;

  @GetMapping("/login")
  public String loginForm(Model model) {
    var userForm = new UserForm();
    model.addAttribute("userForm", userForm);
    return "login-form";
  }

  @PostMapping("/login/save")
  public String login(
      Model model,
      @ModelAttribute UserForm userForm, // sem necessidade de validar o formulario
      HttpSession session) {
    var user = userService.login(userForm.getName(), userForm.getPassword());
    session.setAttribute("userId", user.getId());
    return "redirect:/dashboard";
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    var userForm = new UserForm();
    model.addAttribute("userForm", userForm);
    return "register-form";
  }

  @PostMapping("/register/save")
  public String saveUser(
      Model model,
      @Valid @ModelAttribute UserForm userForm,
      HttpSession session) {
    var user = userService.createUser(userForm);
    session.setAttribute("userId", user.getId());
    return "redirect:/dashboard";
  }

  @GetMapping("/dashboard")
  public String dashboard(Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var user = userService.findById(userId);
    var tasks = taskService.getAllTasks(userId);
    model.addAttribute("user", user);
    model.addAttribute("tasks", tasks);
    return "dashboard";
  }
}
