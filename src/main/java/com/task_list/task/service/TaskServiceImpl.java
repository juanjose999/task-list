package com.task_list.task.service;

import com.task_list.exception.MyUserException;
import com.task_list.exception.TaskNotFoundException;
import com.task_list.task.entity.Task;
import com.task_list.task.entity.dto.TaskMapper;
import com.task_list.task.entity.dto.TaskRequestDto;
import com.task_list.task.entity.dto.TaskRequestWithEmailUserDto;
import com.task_list.task.entity.dto.TaskResponseDto;
import com.task_list.task.repository.ITaskRepository;
import com.task_list.user.entity.MyUser;
import com.task_list.user.repository.IMyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {
    
    private final ITaskRepository taskRepository;
    private final IMyUserRepository myUserRepository;
    
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
    public Optional<Set<TaskResponseDto>> findAllTasksByEmail(String email) throws MyUserException {
        Optional<Set<Task>> tasks = taskRepository.findAllTasksByEmail(email);
        Set<TaskResponseDto> taskResponseDtos = new HashSet<>();
        if (tasks.isPresent()) {
            for (Task task : tasks.get()) {
                taskResponseDtos.add(TaskMapper.entityToTaskResponseDto(task));
            }
        }
        return Optional.of(taskResponseDtos);
    }

    @Override
    public TaskResponseDto save(TaskRequestWithEmailUserDto taskRequestDto) throws MyUserException {

        MyUser findUser = getMyUser(taskRequestDto.emailUser());
        Task task = TaskMapper.taskRequestSaveToEntity(taskRequestDto);
        task.setStatus(Task.Status.SIN_REALIZAR);
        task.setPriority(Task.Priority.ALTA);
        task.setDateCreated();
        task.setUser(findUser);
        task = taskRepository.save(task);
        findUser.addTask(task);
        return TaskMapper.entityToTaskResponseDto(taskRepository.save(task));
    }

    public TaskResponseDto update(String id, TaskRequestWithEmailUserDto taskRequestWithEmailUserDto) throws TaskNotFoundException, MyUserException {
        MyUser findUser = getMyUser(taskRequestWithEmailUserDto.emailUser());
        return TaskMapper.entityToTaskResponseDto(
                taskRepository.update(id, TaskMapper.taskRequestSaveToEntity(taskRequestWithEmailUserDto), findUser)
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
