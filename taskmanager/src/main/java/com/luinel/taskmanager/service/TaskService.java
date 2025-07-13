package com.luinel.taskmanager.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.luinel.taskmanager.model.Status;
import com.luinel.taskmanager.model.Task;
import com.luinel.taskmanager.model.User;
import com.luinel.taskmanager.model.form.TaskForm;
import com.luinel.taskmanager.repository.TaskRepository;
import com.luinel.taskmanager.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class TaskService {
  private final TaskRepository taskRepo;
  private final UserRepository userRepo;

  private User getUser(Long id) {
    return userRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
  }

  public String createTask(Long userID, TaskForm taskForm) {
    var user = getUser(userID);
    var task = new Task(taskForm.getTitle(), taskForm.getDescription());

    task.setUser(user);
    user.getTasks().add(task);
    taskRepo.save(task);

    return "Tarefa salva";
  }

  public String updateTask(Long userID, Long taskID, TaskForm taskForm) {
    var user = getUser(userID);
    var task = getTaskById(taskID);
    task.setTitle(taskForm.getTitle());
    task.setDescription(taskForm.getDescription());

    task.setUser(user);
    user.getTasks().add(task);
    taskRepo.save(task);

    return "Tarefa actualizada";
  }

  public String removeTask(Long id) {
    taskRepo.deleteById(id);
    return "Tarefa removida";
  }

  public Task getTaskById(Long id) {
    return taskRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
  }

  public List<Task> getAllTasks(Long userID) {
    return taskRepo.findByUserId(userID);
  }

  public List<Task> getAllTasksByStatus(Long userId, Status status) {
    return taskRepo.findByUserIdAndStatus(userId, status);
  }

  public List<Task> getAllTasksBetween(Long userId, LocalDateTime start, LocalDateTime end) {
    return taskRepo.findByUserIdAndCreatedAtBetween(userId, start, end);
  }

  public List<Task> getTaskByTitle(Long userId, String title) {
    return taskRepo.findByUserIdAndTitleContaining(userId, title);
  }
}
