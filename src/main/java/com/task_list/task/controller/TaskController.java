package com.task_list.task.controller;

import com.task_list.exception.MyUserException;
import com.task_list.exception.TaskNotFoundException;
import com.task_list.task.entity.Task;
import com.task_list.task.entity.dto.TaskRequestDto;
import com.task_list.task.entity.dto.TaskRequestWithEmailUserDto;
import com.task_list.task.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final ITaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAllTasksByEmail(@RequestBody final String email) throws TaskNotFoundException, MyUserException {
        return ResponseEntity.ok(taskService.findTaskByEmail(email));
    }

    @PostMapping
    public ResponseEntity<?> saveTask(@RequestBody final TaskRequestWithEmailUserDto taskRequestDto) throws TaskNotFoundException, MyUserException {
        return ResponseEntity.ok(taskService.save(taskRequestDto));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateTask(@RequestBody final TaskRequestWithEmailUserDto taskRequestDto, @PathVariable String id) throws TaskNotFoundException, MyUserException {
        return ResponseEntity.ok(taskService.update(id, taskRequestDto));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestBody final String id) throws TaskNotFoundException {
        return taskService.deleteById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
