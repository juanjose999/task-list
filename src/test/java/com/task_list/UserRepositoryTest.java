package com.task_list;

import com.task_list.user.entity.MyUser;
import com.task_list.user.repository.IMyUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    private MyUser myUser;

    @Mock
    private IMyUserRepository userRepository;

    @BeforeEach
    void setUp(){
        myUser = MyUser.builder()
                .fullName("Juan Jose Sierra Ortega")
                .email("juanjose@gmail.com")
                .password("12311")
                .dateCreated("2025/1/10 04:30")
                .build();
    }

    @Test
    public void findUserByEmail(){
        String email = "juanjose@gmail.com";

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(myUser));
        Optional<MyUser> findUser = userRepository.findUserByEmail(email);

        assertEquals(Optional.of(myUser), findUser);
        assertEquals(email, findUser.get().getEmail());
        assertEquals(findUser.get().getFullName(), findUser.get().getFullName());
    }

    @Test
    public void findUserByFullName(){
        String fullName = "Juan Jose Sierra Ortega";

        when(userRepository.findByFullName(fullName)).thenReturn(Optional.of(myUser));
        Optional<MyUser> findUser = userRepository.findByFullName(fullName);

        assertEquals(fullName, findUser.get().getFullName());
        assertEquals(myUser.getEmail(), findUser.get().getEmail());
    }

    @Test
    public void saveUser(){

        when(userRepository.save(myUser)).thenReturn(myUser);
        MyUser savedUser = userRepository.save(myUser);

        assertEquals(myUser, savedUser);
        assertEquals(savedUser.getFullName(), myUser.getFullName());
        assertEquals(savedUser.getEmail(), myUser.getEmail());
    }

    @Test
    public void updateUserByEmail(){
        String email = "juan@gmail.com";

        MyUser oldUser = MyUser.builder()
                .fullName("Juan Ortega")
                .email("juanjose@gmail.com")
                .password("12311")
                .dateCreated("2025/1/10 04:30")
                .build();

        MyUser dataToUpdate = MyUser.builder()
                .fullName("Juan Ortega")
                .email("juanjose@gmail.com")
                .password("12311")
                .dateCreated("2025/1/10 04:30")
                .build();

        when(userRepository.updateUserByEmail(email, dataToUpdate)).thenReturn(Optional.of(oldUser));
        Optional<MyUser> updateUser = userRepository.updateUserByEmail(email,dataToUpdate);

        assertEquals("Juan Ortega", oldUser.getFullName());
        assertEquals("juanjose@gmail.com", oldUser.getEmail());
    }

    @Test
    public void deleteUserByEmail(){
        String email = "juanjose@gmail.com";
        when(userRepository.deleteUserByEmail(email)).thenReturn(true);
        boolean userIsDelete = userRepository.deleteUserByEmail(email);
        assertEquals(true, userIsDelete);
    }

}
