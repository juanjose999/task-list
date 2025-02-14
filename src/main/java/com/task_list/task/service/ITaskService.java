package com.task_list.task.service;

import com.task_list.exception.JwtException;
import com.task_list.exception.MyUserException;
import com.task_list.exception.TaskNotFoundException;
import com.task_list.task.entity.dto.TaskRequestDto;
import com.task_list.task.entity.dto.TaskResponseDto;

import java.util.Optional;
import java.util.Set;

public interface ITaskService {
    TaskResponseDto findTaskById(String id) throws TaskNotFoundException;
    TaskResponseDto findTaskByEmail(String email) throws MyUserException, TaskNotFoundException;
    Optional<Set<TaskResponseDto>> findAllTasksByEmail(String token) throws MyUserException, JwtException;
    TaskResponseDto save(TaskRequestDto taskRequestDto, String token) throws MyUserException;
    TaskResponseDto update(String id, TaskRequestDto taskRequestDto, String token) throws TaskNotFoundException, MyUserException;
    boolean deleteById(String id);
}
