package com.luinel.taskmanager.model;

import java.time.LocalDateTime;

import com.luinel.taskmanager.error.InvalidTitleException;

import jakarta.persistence.CascadeType;
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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "tb_tasks")
@Entity
@Data
@NoArgsConstructor
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  private String description;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  private LocalDateTime finishedAt;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @ToString.Exclude
  private User user;

  public Task(String title, String description) {
    if (title == null || title.isBlank()) {
      throw new InvalidTitleException("Titulo não pode estar vazio");
    }

    this.title = title;
    this.description = description;
    this.createdAt = LocalDateTime.now();
    this.status = Status.PENDENTE;
  }

  public void setTitle(String title) {
    if (title == null || title.isBlank()) {
      throw new InvalidTitleException("Titulo não pode estar vazio");
    }
  }
}
