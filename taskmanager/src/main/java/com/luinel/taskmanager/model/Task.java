package com.luinel.taskmanager.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Table(name = "tb_tasks")
@Entity
@Data
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  private String description;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime fineshedAt;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne
  @ToString.Exclude
  private User user;

  public Task(String title, String description) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("Titulo não pode estar vazio");
    }

    this.title = title;
    this.description = description;
    this.created_at = LocalDateTime.now();
    this.status = Status.PENDING;
  }

  public void setTitle(String title) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("Titulo não pode estar vazio");
    }
    this.title = title;
  }
}
