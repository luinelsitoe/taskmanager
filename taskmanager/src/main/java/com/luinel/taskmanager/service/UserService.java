package com.luinel.taskmanager.service;

import org.springframework.stereotype.Service;

import com.luinel.taskmanager.error.DuplicateUserException;
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

  public User findById(Long id) {
    return userRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
  }

  public User createUser(UserForm userForm) {
    if (userRepo.existsByName(userForm.getName())) {
      throw new DuplicateUserException(
          String.format("Usuario com nome %s já existe", userForm.getName()));
    }
    var user = new User(userForm.getName(), userForm.getPassword());
    userRepo.save(user);

    return user;
  }

  public String updateName(Long id, String name) {
    var user = findById(id);
    user.setName(name);
    userRepo.save(user);

    return "Nome actualizado";
  }

  public String updatePassword(Long id, String password) {
    var user = findById(id);
    user.setPassword(password);
    userRepo.save(user);

    return "Palavra-passe actualizada";
  }

  // 🤫
  public User login(String name, String password) {
    var user = userRepo.findByName(name)
        .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

    if (!user.getPassword().equals(password)) {
      throw new IllegalArgumentException("Palavra-passe incorreta");
    }

    return user;
  }
}
