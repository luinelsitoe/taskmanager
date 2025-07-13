package com.luinel.taskmanager.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luinel.taskmanager.model.Status;
import com.luinel.taskmanager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByUserIdAndTitleContaining(Long userId, String title);

  List<Task> findByUserIdAndStatus(Long userID, Status status);

  List<Task> findByUserIdAndCreatedAtBetween(Long userID, LocalDateTime start, LocalDateTime end);

  List<Task> findByUserId(Long userID);
}
