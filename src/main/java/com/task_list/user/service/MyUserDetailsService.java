package com.task_list.user.service;

import com.task_list.user.entity.MyUser;
import com.task_list.user.repository.IMyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final IMyUserRepository myUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> findUser = myUserRepository.findUserByEmail(username);
        if(findUser.isPresent()) {
            return User.builder()
                    .username(findUser.get().getEmail())
                    .password(findUser.get().getPassword())
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException("User not found in the database");
    }
}
