package com.luinel.taskmanager.service;

import java.time.LocalDateTime;
import java.util.List;

import com.luinel.taskmanager.model.Status;
import com.luinel.taskmanager.model.Task;
import com.luinel.taskmanager.model.form.TaskForm;

public interface TaskServiceI {

  String createTask(Long userID, TaskForm taskForm);

  String undoStatus(Long taskID, Long userID);

  String cancelTask(Long taskID, Long userID);

  String finishTask(Long taskID, Long userID);

  String updateTitle(Long taskID, Long userID, String title);

  String updateDescription(Long taskID, Long userID, String description);

  String removeTask(Long id);

  Task getTaskById(Long taskID, Long userID);

  List<Task> getAllTasks(Long userID);

  List<Task> getAllTasksByStatus(Long userId, Status status);

  List<Task> getAllTasksBetween(Long userId, LocalDateTime start, LocalDateTime end);

  List<Task> getTaskByTitle(Long userId, String title);

}