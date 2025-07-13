package com.luinel.taskmanager.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_users")
@Data
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String password;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @ToString.Exclude
  private List<Task> tasks;

  public User(String name, String password) {
    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("Password não pode estar vazia");
    }

    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Nome não pode estar vazio");
    }
    this.name = name;
    this.password = password;
  }

  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Nome não pode estar vazio");
    }
    this.name = name;
  }

  public void setPassword(String password) {
    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("Password não pode estar vazia");
    }
    this.password = password;
  }
}
