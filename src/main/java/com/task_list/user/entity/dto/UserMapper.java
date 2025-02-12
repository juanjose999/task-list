package com.task_list.user.entity.dto;

import com.task_list.user.entity.MyUser;

public class UserMapper {

    public static MyUser requestDtoToEntity(MyUserRequestDto requestDto) {
        return MyUser.builder()
                .fullName(requestDto.fullName())
                .email(requestDto.email())
                .password(requestDto.password())
                .build();
    }

    public static MyUserResponseDto entityToResponseDto(MyUser myUser) {
        return MyUserResponseDto.builder()
                .fullName(myUser.getFullName())
                .email(myUser.getEmail())
                .build();
    }

}
