package com.task_list.task.repository;

import com.task_list.exception.MyUserException;
import com.task_list.task.entity.Task;
import com.task_list.user.entity.MyUser;
import com.task_list.user.repository.IMyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements ITaskRepository{

    private final ITaskRepositoryMongo repositoryMongo;
    private final IMyUserRepository myUserRepository;

    @Override
    public List<Task> findByTaskName(String name, MyUser user) {
        String finalName = name.toLowerCase();
        Set<Task> findTask = repositoryMongo.findAllByUser_Id(user.getId()).orElse(new HashSet<>());
        return findTask.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(name.toLowerCase())).toList();
    }

    @Override
    public Optional<Task> findTaskById(String id) {
        return repositoryMongo.findById(id);
    }

    @Override
    public Optional<Task> findTaskByEmail(String email) {
        return repositoryMongo.findTaskByUser_Email(email);
    }

    @Override
    public Optional<Set<Task>> findAllTasksByEmail(String email) throws MyUserException {
        return repositoryMongo.findAllByUser_Id(getUser(email).getId());
    }

    private final MyUser getUser(String email) throws MyUserException {
        return myUserRepository.findUserByEmail(email)
                .orElseThrow(() -> new MyUserException("User not found"));
    }

    @Override
    public Task save(Task task) {
        return repositoryMongo.save(task);
    }

    @Override
    public Optional<Task> update(String id, Task taskToUpdate, MyUser myUser) {
        Optional<Task> taskOptional = repositoryMongo.findById(id);
        if (taskOptional.isPresent()) {
            if(taskToUpdate.getTitle() != null){
                taskOptional.get().setTitle(taskToUpdate.getTitle());
            }
            if(taskToUpdate.getDescription() != null){
                taskOptional.get().setDescription(taskToUpdate.getDescription());
            }
            if(taskToUpdate.getPriority() != null){
                taskOptional.get().setPriority(taskToUpdate.getPriority());
            }
            if(taskToUpdate.getStatus() != null){
                taskOptional.get().setStatus(taskToUpdate.getStatus());
            }
            repositoryMongo.save(taskOptional.get());
            myUser.addTask(taskOptional.get());
            myUserRepository.save(myUser);
            return Optional.of(taskOptional.get());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(String id) {
        Optional<Task> taskOptional = repositoryMongo.findById(id);
        if (taskOptional.isPresent()) {
            repositoryMongo.delete(taskOptional.get());
            return true;
        }
        return false;
    }
}
