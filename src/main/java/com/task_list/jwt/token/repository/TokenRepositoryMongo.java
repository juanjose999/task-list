package com.task_list.jwt.token.repository;

import com.task_list.jwt.token.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepositoryMongo extends MongoRepository<Token, String> {
    Optional<Token> findByToken(String token);
    List<Token> findByMyUser_Id(String userId);
}
