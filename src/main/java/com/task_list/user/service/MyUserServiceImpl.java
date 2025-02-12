package com.task_list.user.service;

import com.task_list.exception.MyUserException;
import com.task_list.user.entity.MyUser;
import com.task_list.user.entity.dto.MyUserRequestDto;
import com.task_list.user.entity.dto.MyUserResponseDto;
import com.task_list.user.entity.dto.UserMapper;
import com.task_list.user.repository.IMyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class MyUserServiceImpl implements IMyUserService{

    private final IMyUserRepository myUserRepository;

    @Override
    public MyUserResponseDto findUserByEmail(String email) throws MyUserException {
        return UserMapper.entityToResponseDto(myUserRepository.findUserByEmail(email)
                .orElseThrow(() -> new MyUserException("User not found.")));
    }

    @Override
    public MyUserResponseDto findByFullName(String username) throws MyUserException {
        return UserMapper.entityToResponseDto(myUserRepository.findByFullName(username)
                .orElseThrow(() -> new MyUserException("User not found.")));
    }

    @Override
    public MyUserResponseDto save(MyUserRequestDto myUser) {
        return UserMapper.entityToResponseDto(myUserRepository.save(
                UserMapper.requestDtoToEntity(myUser)
        ));
    }

    @Override
    public MyUserResponseDto updateUserByEmail(String email, MyUserRequestDto myUserToUpdate) throws MyUserException {
        return UserMapper.entityToResponseDto(myUserRepository.updateUserByEmail(email, UserMapper.requestDtoToEntity(myUserToUpdate)).
                orElseThrow(() -> new MyUserException("Error in update record.")));
    }

    @Override
    public Boolean deleteUserByEmail(String email) {
        return myUserRepository.deleteUserByEmail(email);
    }
}
