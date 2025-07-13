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

  public String undoTask(Long taskID, Long userID) {
    var task = getTaskById(taskID, userID);
    task.setStatus(Status.PENDENTE);
    taskRepo.save(task);

    return "Tarefa desfeita";
  }

  public String cancelTask(Long taskID, Long userID) {
    var task = getTaskById(taskID, userID);
    task.setStatus(Status.CANCELADA);
    taskRepo.save(task);

    return "Tarefa cancelada";
  }

  public String finishTask(Long userID, Long taskID) {
    var task = getTaskById(taskID, userID);
    task.setFineshedAt(LocalDateTime.now());
    task.setStatus(Status.FEITA);
    taskRepo.save(task);

    return "Tarefa finalizada";
  }

  public String updateTitle(Long taskID, Long userID, String title) {
    var task = getTaskById(taskID, userID);
    task.setTitle(title);
    taskRepo.save(task);

    return "Titulo actualizada";
  }

  public String updateDescription(Long taskID, Long userID, String description) {
    var task = getTaskById(taskID, userID);
    task.setDescription(description);
    taskRepo.save(task);

    return "Descricao actualizada";
  }

  public String removeTask(Long id) {
    taskRepo.deleteById(id);
    return "Tarefa removida";
  }

  public Task getTaskById(Long taskID, Long userID) {
    return taskRepo.findByIdAndUserId(taskID, userID)
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
