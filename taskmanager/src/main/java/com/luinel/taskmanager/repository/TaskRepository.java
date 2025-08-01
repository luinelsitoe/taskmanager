package com.luinel.taskmanager.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luinel.taskmanager.model.Status;
import com.luinel.taskmanager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  Optional<Task> findByIdAndUserId(Long taskID, Long userID);

  List<Task> findByUserIdAndTitleContainingOrderByIdDesc(Long userID, String title);

  List<Task> findByUserIdAndStatusOrderByIdDesc(Long userID, Status status);

  List<Task> findByUserIdAndCreatedAtBetweenOrderByIdDesc(Long userID, LocalDateTime start, LocalDateTime end);

  List<Task> findByUserIdOrderByIdDesc(Long userID);

  void deleteByIdAndUserId(Long taskID, Long userID);
}
