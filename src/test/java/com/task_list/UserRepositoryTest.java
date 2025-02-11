package com.task_list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    private MyUser myUser;

    @Mock
    private MyUserRepository userRepository;

    @BeforeEach
    void setUp(){
        myUser = MyUser.builder()
                .full_name("Juan Jose Sierra Ortega")
                .email("juanjose@gmail.com")
                .password("12311")
                .build();
    }

    @Test
    public void findUserByEmail(){
        String email = "juanjose@gmail.com";

        Optional<MyUser> findUser = userRepository.findByEmail(email);
        assertEquals(findUser.full_name()).equals("Juan Jose Sierra Ortega");
        assert(findUser.get()).isNotNull;
        assertEquals(findUser.email()).equals(email);
    }

    @Test
    public void findUserByFullName(){
        String fullName = "Juan Jose Sierra Ortega";

        Optional<MyUser> findUser = userRepository.findByFullName(email);
        assertEquals(findUser.full_name()).equals("Juan Jose Sierra Ortega");
        assert(findUser.get()).isNotNull;
        assertEquals(findUser.email()).equals(email);
    }

    @Test
    public void saveUser(){

        MyUser savedUser = myUserRepository.save(user);

        assert(savedUser).isNotNull;
        assertEquals(savedUser.full_name()).equals("Juan Jose Sierra Ortega");
        assertEquals(savedUser.email()).equals("juanjose@gmail.com");
        assertEquals(savedUser.password()).equals("12311");
    }

    @Test
    public void updateUserByEmail(){
        String email = "juanjose@gmail.com";
        String newName = "Juancho sierra";

        Optional<MyUser> updateUser = userRespository.updateUserByEmail(email);

        assert(updateUser).isNotNull;
        assertEquals(updateUser.full_name()).equals(newName);
    }

    @Test
    public void deleteUserByEmail(){
        String email = "juanjose@gmail.com";
        boolean userIsDelete = userRepository.deleteByEmail(email);
        assertEquals(true, userIsDelete);
    }

}
