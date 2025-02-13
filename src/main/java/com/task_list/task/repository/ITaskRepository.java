package com.task_list.task.repository;

import com.task_list.exception.MyUserException;
import com.task_list.task.entity.Task;
import com.task_list.user.entity.MyUser;

import java.util.Optional;
import java.util.Set;

public interface ITaskRepository {
    Optional<Task> findTaskById(String id);
    Optional<Task> findTaskByEmail(String email);
    Optional<Set<Task>> findAllTasksByEmail(String email) throws MyUserException;
    Task save(Task task);
    Optional<Task> update(String id, Task taskToUpdate, MyUser myUser);
    boolean delete(String id);
}
