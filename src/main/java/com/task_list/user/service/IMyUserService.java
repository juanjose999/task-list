package com.task_list.user.service;

import com.task_list.exception.JwtException;
import com.task_list.exception.MyUserException;
import com.task_list.user.entity.MyUser;
import com.task_list.user.entity.dto.FormLogin;
import com.task_list.user.entity.dto.MyUserRequestDto;
import com.task_list.user.entity.dto.MyUserResponseDto;

import java.util.Map;
import java.util.Optional;

public interface IMyUserService {
    MyUserResponseDto findUserByEmail(String email) throws MyUserException;
    MyUserResponseDto findByFullName(String username) throws MyUserException;
    Map<String,String> save(MyUserRequestDto myUserRequestDto) throws MyUserException;
    Map<String,String> loginToken(FormLogin formLogin) throws MyUserException;
    Map<String,String> refreshToken(String token) throws MyUserException, JwtException;
    void revokeToken(String token) throws MyUserException, JwtException;
    MyUserResponseDto updateUserByEmail(String email, MyUserRequestDto myUserToUpdate) throws MyUserException;
    Boolean deleteUserByEmail(String token) throws MyUserException, JwtException;
}
