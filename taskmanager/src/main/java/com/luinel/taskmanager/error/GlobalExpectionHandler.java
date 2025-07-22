package com.luinel.taskmanager.error;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExpectionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  public String handleIllegalArgument(IllegalArgumentException ex, RedirectAttributes redirectAttributes,
      HttpServletRequest request) {
    var referer = request.getHeader("referer");

    redirectAttributes.addFlashAttribute("errorMessages", ex.getMessage());
    return "redirect:" + referer;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public String handleValidation(MethodArgumentNotValidException ex, RedirectAttributes redirectAttributes,
      HttpServletRequest request) {
    var referer = request.getHeader("referer");

    var msgs = ex.getBindingResult()
        .getAllErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();

    redirectAttributes.addFlashAttribute("errorMessages", msgs);
    return "redirect:" + referer;
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public String hadleEntityNotFound(EntityNotFoundException ex, RedirectAttributes redirectAttributes,
      HttpServletRequest request) {
    var referer = request.getHeader("referer");

    redirectAttributes.addFlashAttribute("errorMessages", ex.getMessage());
    return "redirect:" + referer;
  }
}
