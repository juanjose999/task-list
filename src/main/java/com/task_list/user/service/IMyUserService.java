package com.task_list.user.service;

import com.task_list.exception.MyUserException;
import com.task_list.user.entity.MyUser;
import com.task_list.user.entity.dto.MyUserRequestDto;
import com.task_list.user.entity.dto.MyUserResponseDto;

import java.util.Optional;

public interface IMyUserService {
    MyUserResponseDto findUserByEmail(String email) throws MyUserException;
    MyUserResponseDto findByFullName(String username) throws MyUserException;
    MyUserResponseDto save(MyUserRequestDto myUserRequestDto);
    MyUserResponseDto updateUserByEmail(String email, MyUserRequestDto myUserToUpdate) throws MyUserException;
    Boolean deleteUserByEmail(String email);
}
