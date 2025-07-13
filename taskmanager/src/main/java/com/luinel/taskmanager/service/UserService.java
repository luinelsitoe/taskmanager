package com.luinel.taskmanager.service;

import java.security.InvalidParameterException;

import org.springframework.stereotype.Service;

import com.luinel.taskmanager.model.User;
import com.luinel.taskmanager.model.form.UserForm;
import com.luinel.taskmanager.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
  private final UserRepository userRepo;

  private User getUser(Long id) {
    return userRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
  }

  public String createUser(UserForm userForm) {
    var user = new User(userForm.getName(), userForm.getPassword());
    userRepo.save(user);

    return "Usuario criado";
  }

  public String updateName(Long id, String name) {
    var user = getUser(id);
    user.setName(name);

    return "Nome actualizado";
  }

  public String updatePassword(Long id, String password) {
    var user = getUser(id);
    user.setPassword(password);

    return "Palavra-passe actualizada";
  }

  // 🤫
  public User login(String name, String password) {
    var user = userRepo.findByName(name)
        .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

    if (user.getPassword().equals(password)) {
      throw new InvalidParameterException("Palavra-passe incorreta");
    }

    return user;
  }
}
