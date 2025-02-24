package com.task_list.task.controller;

import com.task_list.exception.JwtException;
import com.task_list.exception.MyUserException;
import com.task_list.exception.TaskNotFoundException;
import com.task_list.task.entity.dto.TaskRequestDto;
import com.task_list.task.service.ITaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TaskController {

    private final ITaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAllTasksByEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token) throws TaskNotFoundException, MyUserException, JwtException {
        return ResponseEntity.ok(taskService.findAllTasksByEmail(token));
    }

    @GetMapping("/id")
    public ResponseEntity<?> getTaskById(@Valid @RequestParam final String id) throws TaskNotFoundException, MyUserException {
        return ResponseEntity.ok(taskService.findTaskById(id));
    }

    @PostMapping
    public ResponseEntity<?> saveTask(@Valid @RequestBody final TaskRequestDto taskRequestDto,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws TaskNotFoundException, MyUserException {
        if(token == null || !token.startsWith("Bearer ")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid token"));
        return ResponseEntity.ok(taskService.save(taskRequestDto, token));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getTasksByName(@RequestParam String name, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws TaskNotFoundException, MyUserException, JwtException {
        return ResponseEntity.ok(taskService.findByTaskName(name,token));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody final TaskRequestDto taskRequestDto,
                                        @PathVariable String id,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws TaskNotFoundException, MyUserException {
        if(token == null || !token.startsWith("Bearer ")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid token"));
        return ResponseEntity.ok(taskService.update(id, taskRequestDto, token));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable final String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws TaskNotFoundException, MyUserException, JwtException {
        return taskService.deleteById(id, token) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
