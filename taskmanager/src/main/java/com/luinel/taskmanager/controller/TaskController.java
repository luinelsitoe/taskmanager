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

  @GetMapping("/{userId}/{taskId}")
  public String getTaskById(@PathVariable("userId") Long userID, @PathVariable("taskId") Long taskID, Model model) {
    var task = taskService.getTaskById(taskID, userID);
    model.addAttribute("task", task);
    return "fragments/task-fragment";
  }

  @GetMapping("/{userId}")
  public String getAllTasks(@PathVariable Long userId, Model model) {
    var tasks = taskService.getAllTasks(userId);
    model.addAttribute("tasks", tasks);
    return "fragments/tasks/";
  }

  @GetMapping("/status/{userId}/")
  public String getAllTasksByStatus(@PathVariable Long userId, @RequestParam Status status, Model model) {
    var tasks = taskService.getAllTasksByStatus(userId, status);
    model.addAttribute("tasks", tasks);
    return "fragments/tasks/";
  }

  @GetMapping("/date/{userId}")
  public String getAllTasksBetween(@PathVariable Long userId, @RequestParam LocalDateTime start,
      @RequestParam LocalDateTime end, Model model) {
    var tasks = taskService.getAllTasksBetween(userId, start, end);
    model.addAttribute("tasks", tasks);
    return "fragments/tasks/";
  }

  @GetMapping("/title/{userId}")
  public String getTaskByTitle(@PathVariable Long userId, @RequestParam String title, Model model) {
    var tasks = taskService.getTaskByTitle(userId, title);
    model.addAttribute("tasks", tasks);
    return "fragments/tasks/";
  }

  @GetMapping("/form")
  public String taskForm(Model model) {
    var taskForm = new TaskForm();
    model.addAttribute("taskForm", taskForm);
    return "task-form";
  }

  @PostMapping("/form/save")
  public String createTask(Model model, @Valid @ModelAttribute TaskForm taskForm, HttpSession session) {
    var userId = (Long) session.getAttribute("userId");

    if (userId == null) {
      return "redirect:/login";
    }

    taskService.createTask(userId, taskForm);
    return "redirect:/dashboard";
  }

  // String undoStatus(Long taskID, Long userID);

  // String cancelTask(Long taskID, Long userID);

  // String finishTask(Long taskID, Long userID);

  // String updateTitle(Long taskID, Long userID, String title);

  // String updateDescription(Long taskID, Long userID, String description);

  // String removeTask(Long id);

}
