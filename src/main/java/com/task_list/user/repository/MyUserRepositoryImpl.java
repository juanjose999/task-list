package com.task_list.user.repository;

import com.task_list.user.entity.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyUserRepositoryImpl implements IMyUserRepository {

    private final IMyUserRepositoryMongo repository;

    @Override
    public Optional<MyUser> findUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<MyUser> findByFullName(String fullName) {
        return repository.findByFullName(fullName);
    }

    @Override
    public MyUser save(MyUser myUser) {
        myUser.setDateCreated();
        return repository.save(myUser);
    }

    @Override
    public Optional<MyUser> updateUserByEmail(String email, MyUser myUserToUpdate) {
        Optional<MyUser> findUser = findUserByEmail(email);
        if (findUser.isPresent()) {
            MyUser myUser = findUser.get();
            String name = myUserToUpdate.getFullName();
            myUser.setFullName(name);
            repository.save(myUser);
            return Optional.of(myUser);
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteUserByEmail(String email) {
        Optional<MyUser> user = repository.findByEmail(email);
        if (user.isPresent()) {
            repository.delete(user.get());
            return true;
        }
        return false;
    }
}
