package com.task_list.user;

import com.task_list.exception.MyUserException;
import com.task_list.user.entity.dto.MyUserRequestDto;
import com.task_list.user.entity.dto.MyUserResponseDto;
import com.task_list.user.service.MyUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private MyUserServiceImpl myUserService;

    private MyUserRequestDto myUserRequestDto;
    private MyUserResponseDto userResponseDto;

    @BeforeEach
    void setUp(){
        myUserRequestDto = MyUserRequestDto.builder()
                .fullName("Juan Jose Sierra Ortega")
                .email("juanjose@gmail.com")
                .password("12311")
                .build();

        userResponseDto = MyUserResponseDto.builder()
                .fullName("Juan Jose Sierra Ortega")
                .email("juanjose@gmail.com")
                .build();
    }

    @Test
    public void findUserByEmail() throws MyUserException {
        String email = "juanjose@gmail.com";

        when(myUserService.findUserByEmail(email)).thenReturn(userResponseDto);
        MyUserResponseDto findUser = myUserService.findUserByEmail(email);

        assertEquals("Juan Jose Sierra Ortega", findUser.fullName());
        assertEquals(email, findUser.email());
    }

    @Test
    public void findUserByFullName() throws MyUserException {
        String fullName = "Juan Jose Sierra Ortega";

        when(myUserService.findByFullName(fullName)).thenReturn(userResponseDto);
        MyUserResponseDto findUser = myUserService.findByFullName(fullName);

        assertEquals(fullName, findUser.fullName());
        assertEquals(myUserRequestDto.email(), findUser.email());
    }

    @Test
    public void saveUser(){
        when(myUserService.save(myUserRequestDto)).thenReturn(userResponseDto);
        MyUserResponseDto savedUser = myUserService.save(myUserRequestDto);

        assertEquals(myUserRequestDto.fullName(), savedUser.fullName());
        assertEquals(myUserRequestDto.email(), savedUser.email());
    }

    @Test
    public void updateUserByEmail() throws MyUserException {
        String email = "juan@gmail.com";

        MyUserRequestDto userUpdate = MyUserRequestDto.builder()
                .fullName("Juan Ortega")
                .password("12311")
                .build();

        MyUserResponseDto dataToUpdate = MyUserResponseDto.builder()
                .fullName("Juan Ortega")
                .email("juanjose@gmail.com")
                .build();

        when(myUserService.updateUserByEmail(email, userUpdate)).thenReturn(dataToUpdate);
        MyUserResponseDto updateUser = myUserService.updateUserByEmail(email,userUpdate);

        assertEquals("Juan Ortega", updateUser.fullName() );
    }

    @Test
    public void deleteUserByEmail(){
        String email = "juanjose@gmail.com";
        when(myUserService.deleteUserByEmail(email)).thenReturn(true);
        boolean userIsDelete = myUserService.deleteUserByEmail(email);
        assertEquals(true, userIsDelete);
    }

}
