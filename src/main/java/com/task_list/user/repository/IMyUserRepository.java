package com.task_list.user.repository;

import com.task_list.user.entity.MyUser;

import java.util.Optional;

public interface IMyUserRepository {
    Optional<MyUser> findUserByEmail(String email);
    Optional<MyUser> findByFullName(String username);
    MyUser save(MyUser myUser);
    Optional<MyUser> updateUserByEmail(String email, MyUser myUserToUpdate);
    Boolean deleteUserByEmail(String email);
}
