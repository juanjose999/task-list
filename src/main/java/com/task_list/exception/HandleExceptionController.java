package com.task_list.exception;

import com.mongodb.MongoWriteException;
import com.mongodb.WriteError;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
@ControllerAdvice
public class HandleExceptionController {



    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<?> handleMongoWriteException(MongoWriteException ex) {
        Map<Object, Object> errorResponse =  Map.of(
                "message", "Error en guardar en base de datos.",
                "details", "Ya se encontro el email registrado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
