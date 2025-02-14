package com.task_list.exception;

import com.mongodb.MongoWriteException;
import com.mongodb.WriteError;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
@ControllerAdvice
public class HandleExceptionController {

    @ExceptionHandler(MyUserException.class)
    public ResponseEntity<?> handleMyUserException(final MyUserException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFoundException(final TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleException(JwtException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<?> handleException(UnsupportedJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error", "Invalid credentials"));
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<?> handleMongoWriteException(MongoWriteException ex) {
        Map<Object, Object> errorResponse =  Map.of(
                "message", "Error en guardar en base de datos.",
                "details", "Ya se encontro el email registrado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
