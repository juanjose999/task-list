package com.task_list.task.service;

import com.task_list.exception.MyUserException;
import com.task_list.exception.TaskNotFoundException;
import com.task_list.task.entity.dto.TaskRequestDto;
import com.task_list.task.entity.dto.TaskRequestWithEmailUserDto;
import com.task_list.task.entity.dto.TaskResponseDto;
import com.task_list.user.entity.MyUser;

import java.util.Optional;
import java.util.Set;

public interface ITaskService {
    TaskResponseDto findTaskById(String id) throws TaskNotFoundException;
    TaskResponseDto findTaskByEmail(String email) throws MyUserException, TaskNotFoundException;
    Optional<Set<TaskResponseDto>> findAllTasksByEmail(String email) throws MyUserException;
    TaskResponseDto save(TaskRequestWithEmailUserDto taskRequestDto) throws MyUserException;
    TaskResponseDto update(String id, TaskRequestWithEmailUserDto taskToUpdate) throws TaskNotFoundException, MyUserException;
    boolean deleteById(String id);
}
