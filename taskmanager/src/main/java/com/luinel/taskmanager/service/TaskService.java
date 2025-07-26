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

    return "Tarefa criada";
  }

  public String undoStatus(Long taskID, Long userID) {
    var task = getTaskById(taskID, userID);
    task.setStatus(Status.PENDENTE);
    taskRepo.save(task);

    return "Tarefa pendente";
  }

  public String cancelTask(Long taskID, Long userID) {
    var task = getTaskById(taskID, userID);
    task.setStatus(Status.CANCELADA);
    taskRepo.save(task);

    return "Tarefa cancelada";
  }

  public String finishTask(Long taskID, Long userID) {
    var task = getTaskById(taskID, userID);
    task.setFinishedAt(LocalDateTime.now());
    task.setStatus(Status.FEITA);
    taskRepo.save(task);

    return "Tarefa finalizada";
  }

  public String updateTask(Long taskID, Long userID, TaskForm taskForm) {
    var task = getTaskById(taskID, userID);
    task.setTitle(taskForm.getTitle());
    task.setDescription(taskForm.getDescription());
    task.setCreatedAt(taskForm.getCreatedAt());
    task.setFinishedAt(taskForm.getFinishedAt());
    task.setStatus(taskForm.getStatus());
    taskRepo.save(task);

    return "Tarefa actualizada";
  }

  public String removeTask(Long userID, Long taskID) {
    taskRepo.deleteByIdAndUserId(taskID, userID);
    return "Tarefa removida";
  }

  public Task getTaskById(Long taskID, Long userID) {
    return taskRepo.findByIdAndUserId(taskID, userID)
        .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
  }

  public List<Task> getAllTasks(Long userID) {
    return taskRepo.findByUserIdOrderByIdDesc(userID);
  }

  public List<Task> getAllTasksByStatus(Long userId, Status status) {
    return taskRepo.findByUserIdAndStatusOrderByIdDesc(userId, status);
  }

  public List<Task> getAllTasksBetween(Long userId, LocalDateTime start, LocalDateTime end) {
    return taskRepo.findByUserIdAndCreatedAtBetweenOrderByIdDesc(userId, start, end);
  }

  public List<Task> getTaskByTitle(Long userId, String title) {
    return taskRepo.findByUserIdAndTitleContainingOrderByIdDesc(userId, title);
  }
}
