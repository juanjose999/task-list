package com.task_list.user.repository;

import com.task_list.user.entity.MyUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IMyUserRepositoryMongo extends MongoRepository<MyUser, String> {
    Optional<MyUser> findByEmail(String email);
    Optional<MyUser> findByFullName(String username);
}
