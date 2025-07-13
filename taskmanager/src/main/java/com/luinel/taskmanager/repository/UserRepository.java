package com.luinel.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luinel.taskmanager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByName(String name);
}
