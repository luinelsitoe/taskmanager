package com.luinel.taskmanager.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luinel.taskmanager.model.Status;
import com.luinel.taskmanager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByTitleContaining(String title);

  List<Task> findByStatus(Status status);

  List<Task> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
