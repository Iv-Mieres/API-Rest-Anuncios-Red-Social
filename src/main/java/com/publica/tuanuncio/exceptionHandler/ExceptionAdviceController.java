package com.publica.tuanuncio.exceptionHandler;

import com.publica.tuanuncio.exceptionHandler.model.BadRequestException;
import com.publica.tuanuncio.exceptionHandler.model.ErrorDetails;
import com.publica.tuanuncio.model.Usuario;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionAdviceController {


    //METODO PARA CAPTURAR LAS EXCEPTIONS DE SPRING VALIDATION - "MethodArgumentNotValidException"
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validException(MethodArgumentNotValidException methodEx) {
        Map<String, String> mjError = new HashMap<>();
        methodEx.getBindingResult()
                .getFieldErrors()
                .forEach(errorEx -> mjError.put(errorEx.getField(), errorEx.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mjError);
    }

    //METODO PARA CAPTURAR LAS EXCEPTIONS LANZADAS POR "BadRequestException" Y "Exception"
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, Exception.class})
    public ResponseEntity<ErrorDetails> badRequestExceptions(Exception badEx) {
        var error = new ErrorDetails();
        error.setStatus(HttpStatus.BAD_REQUEST.value() + " BAD_REQUEST");
        error.setMessage(badEx.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> violationExceptions(DataIntegrityViolationException violationEx) {
        var error = new ErrorDetails();
        error.setStatus(HttpStatus.BAD_REQUEST.value() + " BAD_REQUEST");
        error.setMessage(violationEx.getCause().getMessage() + ": es posible que los datos hayan sido ingresados con anterioridad.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    //METODO PARA CAPTURAR LAS EXCEPTIONS LANZADAS POR "MethodArgumentTypeMismatchException"
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> typeMismatchExceptions() {
        var error = new ErrorDetails();
        error.setStatus(HttpStatus.BAD_REQUEST.value() + " BAD_REQUEST");
        error.setMessage("Error de tipeo. Revise el tipo de dato ingresado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // ========= SPRING SECURITY EXCEPTIONS ===========


    //METODO PARA CAPTURAR LAS EXCEPTIONS LANZADAS POR "BadCredencialsException"
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> badCredetialsException(BadCredentialsException badCredentialsException) {
        ErrorDetails error = new ErrorDetails();
        error.setStatus(HttpStatus.UNAUTHORIZED.value() + " UNAUTHORIZED");
        error.setMessage(badCredentialsException.getLocalizedMessage() + ": username o password incorrecto.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    //METODO PARA CAPTURAR LAS EXCEPTIONS LANZADAS POR "AccessDeniedException"
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> unauthorizedException(HttpSession session, AccessDeniedException accessDeniedException) {
        Usuario user = (Usuario) session.getAttribute("usersession");
        ErrorDetails error = new ErrorDetails();

        if (Objects.isNull(user)) {
            // Controla el acceso si el usuario NO está logueado
            error.setStatus(HttpStatus.UNAUTHORIZED.value() + " UNAUTHORIZED");
            error.setMessage(accessDeniedException.getLocalizedMessage() + ": debe estar logueado.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } else {
            // Controla el acceso si el usuario ESTÁ logueado
            error.setStatus(HttpStatus.FORBIDDEN.value() + " FORBIDDEN");
            error.setMessage(accessDeniedException.getLocalizedMessage() + ": no posee los permisos necesarios para acceder.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

    }

}
