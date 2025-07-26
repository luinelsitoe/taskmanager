package com.luinel.taskmanager.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luinel.taskmanager.model.Status;
import com.luinel.taskmanager.model.form.TaskForm;
import com.luinel.taskmanager.service.TaskService;
import com.luinel.taskmanager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;
  private final UserService userService;

  @GetMapping("/form")
  public String taskForm(Model model, HttpSession session) {
    var taskForm = new TaskForm();
    var userId = (Long) session.getAttribute("userId");
    var user = userService.findById(userId);
    model.addAttribute("user", user);
    model.addAttribute("taskForm", taskForm);
    return "task-form";
  }

  @PostMapping("/form/save")
  public String createTask(@Valid @ModelAttribute TaskForm taskForm,
      HttpSession session, RedirectAttributes redirectAttributes) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.createTask(userId, taskForm);
    redirectAttributes.addFlashAttribute("message", msg);
    return "redirect:/dashboard";
  }

  @GetMapping("/update-form/{taskId}")
  public String updateForm(@PathVariable("taskId") Long taskId,
      HttpSession session, Model model) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var task = taskService.getTaskById(taskId, userId);
    var taskForm = new TaskForm(
        task.getTitle(),
        task.getDescription(),
        task.getCreatedAt(),
        task.getFinishedAt(),
        task.getStatus());
    var user = userService.findById(userId);

    model.addAttribute("user", user);
    model.addAttribute("taskId", task.getId());
    model.addAttribute("taskForm", taskForm);
    model.addAttribute("statusList", Status.values());
    return "task-update-form";
  }

  @PostMapping("/update-form/save/{taskId}")
  public String updateTask(@PathVariable("taskId") Long taskId,
      @ModelAttribute TaskForm taskForm,
      RedirectAttributes redirectAttributes,
      HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.updateTask(taskId, userId, taskForm);
    redirectAttributes.addFlashAttribute("message", msg);
    return "redirect:/tasks/" + taskId;
  }

  @GetMapping("/undo/{taskId}")
  public String undoStatus(@PathVariable("taskId") Long taskId,
      RedirectAttributes redirectAttributes, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.undoStatus(taskId, userId);
    redirectAttributes.addFlashAttribute("message", msg);
    return "redirect:/tasks/" + taskId;
  }

  @GetMapping("/cancel/{taskId}")
  public String cancelStatus(@PathVariable("taskId") Long taskId,
      RedirectAttributes redirectAttributes, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.cancelTask(taskId, userId);
    redirectAttributes.addFlashAttribute("message", msg);
    return "redirect:/tasks/" + taskId;
  }

  @GetMapping("/finish/{taskId}")
  public String finishTask(@PathVariable("taskId") Long taskId,
      RedirectAttributes redirectAttributes, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.finishTask(taskId, userId);
    redirectAttributes.addFlashAttribute("message", msg);
    return "redirect:/tasks/" + taskId;
  }

  @GetMapping("/{taskId}")
  public String getTaskById(@PathVariable("taskId") Long taskId, Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var task = taskService.getTaskById(taskId, userId);
    var user = userService.findById(userId);
    model.addAttribute("user", user);
    model.addAttribute("task", task);
    return "task-card";
  }

  @GetMapping("/search")
  public String getAllTasks(Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var tasks = taskService.getAllTasks(userId);
    var user = userService.findById(userId);
    model.addAttribute("user", user);
    model.addAttribute("tasks", tasks);
    model.addAttribute("statusList", Status.values());
    return "dashboard";
  }

  @GetMapping(value = "/search", params = "status")
  public String getAllTasksByStatus(@RequestParam("status") Status status,
      Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var tasks = taskService.getAllTasksByStatus(userId, status);
    var user = userService.findById(userId);
    model.addAttribute("user", user);
    model.addAttribute("tasks", tasks);
    model.addAttribute("statusList", Status.values());
    return "dashboard";
  }

  @GetMapping(value = "/search", params = { "start", "end" })
  public String getAllTasksBetween(@RequestParam("start") LocalDateTime start,
      @RequestParam("end") LocalDateTime end, Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var tasks = taskService.getAllTasksBetween(userId, start, end);
    var user = userService.findById(userId);
    model.addAttribute("user", user);
    model.addAttribute("tasks", tasks);
    model.addAttribute("statusList", Status.values());
    return "dashboard";
  }

  @GetMapping(value = "/search", params = "title")
  public String getTaskByTitle(@RequestParam("title") String title,
      Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var tasks = taskService.getTaskByTitle(userId, title);
    var user = userService.findById(userId);
    model.addAttribute("user", user);
    model.addAttribute("tasks", tasks);
    model.addAttribute("statusList", Status.values());
    return "dashboard";
  }

  @GetMapping("/delete/{taskId}")
  public String removeTask(@PathVariable("taskId") Long taskId, HttpSession session,
      RedirectAttributes redirectAttributes) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.removeTask(userId, taskId);
    redirectAttributes.addFlashAttribute("message", msg);
    return "redirect:/dashboard";
  }
}
