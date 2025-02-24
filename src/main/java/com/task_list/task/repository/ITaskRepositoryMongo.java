package com.task_list.task.repository;

import com.task_list.task.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITaskRepositoryMongo extends MongoRepository<Task, String> {
    Optional<Set<Task>> findAllByUser_Id(String id);
    Optional<Task> findTaskByUser_Email(String email);
    String id(String id);
}
