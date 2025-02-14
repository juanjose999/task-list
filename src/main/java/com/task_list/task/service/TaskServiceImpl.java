package com.task_list.task.service;

import com.task_list.exception.JwtException;
import com.task_list.exception.MyUserException;
import com.task_list.exception.TaskNotFoundException;
import com.task_list.jwt.JwtService;
import com.task_list.task.entity.Task;
import com.task_list.task.entity.dto.TaskMapper;
import com.task_list.task.entity.dto.TaskRequestDto;
import com.task_list.task.entity.dto.TaskResponseDto;
import com.task_list.task.repository.ITaskRepository;
import com.task_list.user.entity.MyUser;
import com.task_list.user.repository.IMyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {
    
    private final ITaskRepository taskRepository;
    private final IMyUserRepository myUserRepository;
    private final JwtService jwtService;
    
    @Override
    public TaskResponseDto findTaskById(String id) throws TaskNotFoundException {
        return TaskMapper.entityToTaskResponseDto(
                taskRepository.findTaskById(id)
                        .orElseThrow(() -> new TaskNotFoundException("Task not found."))
        );
    }

    @Override
    public TaskResponseDto findTaskByEmail(String email) throws MyUserException, TaskNotFoundException {
        MyUser findUser = getMyUser(email);
        return TaskMapper.entityToTaskResponseDto(
                taskRepository.findTaskByEmail(findUser.getEmail())
                        .orElseThrow(() -> new TaskNotFoundException("Task not found."))
        );
    }

    @Override
    public Optional<Set<TaskResponseDto>> findAllTasksByEmail(String token) throws MyUserException, JwtException {
        if(token == null || !token.startsWith("Bearer ")) throw new JwtException("Invalid token");
        MyUser findUser = getMyUser(jwtService.extractUserEmail(token.substring(7)));
        Optional<Set<Task>> tasks = taskRepository.findAllTasksByEmail(findUser.getEmail());
        Set<TaskResponseDto> taskResponseDtos = new HashSet<>();
        if (tasks.isPresent()) {
            for (Task task : tasks.get()) {
                taskResponseDtos.add(TaskMapper.entityToTaskResponseDto(task));
            }
        }
        return Optional.of(taskResponseDtos);
    }

    @Override
    public TaskResponseDto save(TaskRequestDto taskRequestDto, String token) throws MyUserException {
        String emailUser = jwtService.extractUserEmail(token.substring(7));
        MyUser findUser = getMyUser(emailUser);
        Task task = TaskMapper.taskRequestToEntity(taskRequestDto);
        task.setStatus(Task.Status.SIN_REALIZAR);
        task.setPriority(Task.Priority.ALTA);
        task.setDateCreated();
        task.setUser(findUser);
        task = taskRepository.save(task);
        findUser.addTask(task);
        return TaskMapper.entityToTaskResponseDto(taskRepository.save(task));
    }

    @Override
    public TaskResponseDto update(String id, TaskRequestDto taskRequestDto, String token) throws TaskNotFoundException, MyUserException {
        MyUser findUser = getMyUser(jwtService.extractUserEmail(token.substring(7)));
        return TaskMapper.entityToTaskResponseDto(
                taskRepository.update(id, TaskMapper.taskRequestToEntity(taskRequestDto), findUser)
                        .orElseThrow(() -> new TaskNotFoundException("task not found."))
        );
    }

    @Override
    public boolean deleteById(String id) {
        return taskRepository.delete(id);
    }

    private MyUser getMyUser(String email) throws MyUserException {
        return myUserRepository.findUserByEmail(email)
                .orElseThrow(() -> new MyUserException("User not found."));
    }
}
