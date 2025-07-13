package com.luinel.taskmanager.model.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {
  @NotBlank(message = "Nome não pode estar vazio")
  private String name;

  @NotBlank(message = "Palavra-passe não pode estar vazia")
  @Length(min = 6, message = "Password deve conter no minimo 6 caracteres")
  private String password;
}
