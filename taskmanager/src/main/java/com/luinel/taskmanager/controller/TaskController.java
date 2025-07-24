package com.luinel.taskmanager.controller;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luinel.taskmanager.model.Status;
import com.luinel.taskmanager.model.form.TaskForm;
import com.luinel.taskmanager.service.TaskService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @GetMapping("/form")
  public String taskForm(Model model) {
    var taskForm = new TaskForm();
    model.addAttribute("taskForm", taskForm);
    return "task-form";
  }

  @PostMapping("/form/save")
  public String createTask(@Valid @ModelAttribute TaskForm taskForm, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    taskService.createTask(userId, taskForm);
    return "redirect:/dashboard";
  }

  @GetMapping("/update-form/{taskId}")
  public String updateForm(@PathVariable("taskId") Long taskId,
      HttpSession session, Model model) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var statusList = Arrays.asList(Status.values());
    var task = taskService.getTaskById(taskId, userId);

    var taskForm = new TaskForm(
        task.getTitle(),
        task.getDescription(),
        task.getCreatedAt(),
        task.getFinishedAt(),
        task.getStatus());
    model.addAttribute("taskId", task.getId());
    model.addAttribute("taskForm", taskForm);
    model.addAttribute("statusList", statusList);
    return "task-update-form";
  }

  @PostMapping("/update-form/save/{taskId}")
  public String updateTask(@PathVariable("taskId") Long taskId,
      @ModelAttribute TaskForm taskForm, Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.updateTask(taskId, userId, taskForm);
    model.addAttribute("message", msg);
    return "redirect:/dashboard";
  }

  @GetMapping("/{taskId}")
  public String getTaskById(@PathVariable("taskId") Long taskId, Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var task = taskService.getTaskById(taskId, userId);
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
    model.addAttribute("tasks", tasks);
    return "/fragments/tasks-fragment";
  }

  @GetMapping(value = "/search", params = "status")
  public String getAllTasksByStatus(@RequestParam("status") Status status,
      Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var tasks = taskService.getAllTasksByStatus(userId, status);
    model.addAttribute("tasks", tasks);
    return "/fragments/tasks-fragment";
  }

  @GetMapping(value = "/search", params = { "start", "end" })
  public String getAllTasksBetween(@RequestParam("start") LocalDateTime start,
      @RequestParam("end") LocalDateTime end, Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var tasks = taskService.getAllTasksBetween(userId, start, end);
    model.addAttribute("tasks", tasks);
    return "/fragments/tasks-fragment";
  }

  @GetMapping(value = "/search", params = "title")
  public String getTaskByTitle(@RequestParam("title") String title,
      Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var tasks = taskService.getTaskByTitle(userId, title);
    model.addAttribute("tasks", tasks);
    return "/fragments/tasks-fragment";
  }

  @GetMapping("/undo/{taskId}")
  public String undoStatus(@PathVariable("taskId") Long taskId,
      Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.undoStatus(taskId, userId);
    model.addAttribute("message", msg);
    return "redirect:/tasks/" + taskId;
  }

  @GetMapping("/cancel/{taskId}")
  public String cancelStatus(@PathVariable("taskId") Long taskId,
      Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.cancelTask(taskId, userId);
    model.addAttribute("message", msg);
    return "redirect:/tasks/" + taskId;
  }

  @GetMapping("/finish/{taskId}")
  public String finishTask(@PathVariable("taskId") Long taskId,
      Model model, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.finishTask(taskId, userId);
    model.addAttribute("message", msg);
    return "redirect:/tasks/" + taskId;
  }

  @GetMapping("/delete/{taskId}")
  public String removeTask(@PathVariable("taskId") Long taskId, HttpSession session, Model model) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    var msg = taskService.removeTask(taskId, userId);
    model.addAttribute("message", msg);
    return "redirect:/dashboard";
  }
}
