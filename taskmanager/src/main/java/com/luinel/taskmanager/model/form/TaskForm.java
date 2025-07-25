package com.luinel.taskmanager.model.form;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.luinel.taskmanager.model.Status;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskForm {
  @NotBlank(message = "Titulo não pode estar vazio")
  @Length(min = 3, message = "Titulo deve conter no minimo 3 caracteres")
  private String title;

  private String description;

  private LocalDateTime createdAt;

  private LocalDateTime finishedAt;

  private Status status;

  public TaskForm(String title, String description) {
    this.title = title;
    this.description = description;
  }
}
