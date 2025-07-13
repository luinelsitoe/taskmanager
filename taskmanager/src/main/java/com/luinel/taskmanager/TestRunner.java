package com.luinel.taskmanager;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.luinel.taskmanager.model.Status;
import com.luinel.taskmanager.model.form.TaskForm;
import com.luinel.taskmanager.model.form.UserForm;
import com.luinel.taskmanager.service.TaskService;
import com.luinel.taskmanager.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class TestRunner implements CommandLineRunner {

  private final TaskService taskService;
  private final UserService userService;

  @Override
  public void run(String... args) throws Exception {
    testUserService();
    testTaskService();
  }

  private void testTaskService() {
    System.out.println("######## TESTANDO TASKSERVICE #######");

    System.out.println();
    System.out.println(">>> Criando Tarefa");
    // var msg1 = taskService.createTask(1L, new TaskForm("Titulo1", "Descricao1"));
    // System.out.println(msg1);
    // taskService.createTask(1L, new TaskForm("Titulo2", "Descricao2"));
    // taskService.createTask(1L, new TaskForm("Titulo3", "Descricao3"));
    // taskService.createTask(1L, new TaskForm("Titulo4", "Descricao4"));
    // taskService.createTask(1L, new TaskForm("Titulo5", "Descricao5"));
    // taskService.createTask(1L, new TaskForm("Titulo6", "Descricao6"));
    // taskService.createTask(1L, new TaskForm("Titulo7", "Descricao7"));

    try {
      taskService.createTask(1L, new TaskForm("", "Descricao1"));
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    System.out.println();
    System.out.println(">>> Finalizando Tarefa");
    var msg3 = taskService.finishTask(2L, 1L);
    System.out.println(msg3);
    var msg4 = taskService.finishTask(3L, 1L);
    System.out.println(msg4);

    System.out.println();
    System.out.println(">>> Cancelando Tarefa");
    var msg5 = taskService.cancelTask(4L, 1L);
    System.out.println(msg5);
    var msg6 = taskService.cancelTask(5L, 1L);
    System.out.println(msg6);

    System.out.println();
    System.out.println(">>> Desfazendo Status");
    var msg7 = taskService.undoStatus(4L, 1L);
    System.out.println(msg7);

    System.out.println(">>> Actualizando Titulo e descricao");
    var msg8 = taskService.updateTitle(1L, 1L, "TituloNovo");
    System.out.println(msg8);
    var msg9 = taskService.updateDescription(1L, 1L, "DescNova");
    System.out.println(msg9);

    System.out.println(">>> Buscando tarefa");
    var task1 = taskService.getTaskById(1L, 1L);
    System.out.println(task1);

    System.out.println();
    System.out.println(">>> Buscando tarefas");
    var tasks1 = taskService.getAllTasks(1L);
    System.out.println(tasks1);
    var tasks2 = taskService.getAllTasksByStatus(1L, Status.PENDENTE);
    System.out.println(tasks2);
    var tasks3 = taskService.getTaskByTitle(1L, "Titulo2");
    System.out.println(tasks3);
    var tasks4 = taskService.getAllTasksBetween(1L, LocalDateTime.of(2025, 7, 13, 0, 0, 0),
        LocalDateTime.of(2025, 7, 14, 0, 0, 0));
    System.out.println(tasks4);
  }

  private void testUserService() {
    System.out.println("######## TESTANDO USERSERVICE #######");

    System.out.println();
    System.out.println(">>> Criando user");
    // var msg1 = userService.createUser(new UserForm("Luinel", "1234"));
    // System.out.println(msg1);

    try {
      userService.createUser(new UserForm("", ""));
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    System.out.println();
    System.out.println(">>> Fazendo Login");
    var user = userService.login("Luinel", "1234");
    System.out.println(user);

    try {
      userService.login("", "1234");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    System.out.println();
    System.out.println(">>> Actualizando nome e senha");
    var msg4 = userService.updateName(1L, "Sitoe");
    System.out.println(msg4);

    try {
      userService.updatePassword(0L, "Sitoe");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

}