package com.luinel.taskmanager.model.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskForm {
  @NotBlank(message = "Titulo não pode estar vazio")
  @Length(min = 3, message = "Titulo deve ter no minimo 3 caracteres")
  private String title;

  private String description;
}
