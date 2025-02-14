package com.task_list.user.service;

import com.task_list.exception.JwtException;
import com.task_list.exception.MyUserException;
import com.task_list.jwt.JwtService;
import com.task_list.jwt.token.entity.Token;
import com.task_list.jwt.token.repository.TokenRepositoryMongo;
import com.task_list.user.entity.MyUser;
import com.task_list.user.entity.dto.FormLogin;
import com.task_list.user.entity.dto.MyUserRequestDto;
import com.task_list.user.entity.dto.MyUserResponseDto;
import com.task_list.user.entity.dto.UserMapper;
import com.task_list.user.repository.IMyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class MyUserServiceImpl implements IMyUserService{

    private final IMyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepositoryMongo tokenRepositoryMongo;


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
        MyUser myUserEntity = UserMapper.requestDtoToEntity(myUser);
        myUserEntity.setPassword(passwordEncoder.encode(myUser.password()));
        return UserMapper.entityToResponseDto(myUserRepository.save(
                myUserEntity
        ));
    }

    @Override
    public Map<String,String> loginToken(FormLogin formLogin) throws MyUserException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        formLogin.email(),formLogin.password()
                )
        );
       if(authentication.isAuthenticated()){
           MyUser myUser = (MyUser) authentication.getPrincipal();
           String access = jwtService.generateTokenAccess(myUser);
           String refresh = jwtService.generateTokenRefresh(myUser);
           saveToken(myUser,refresh);
           return  Map.of("access_token",access,"refresh_token",refresh);
       }
        return Map.of("Error", "Invalid credentials");
    }

    private void saveToken(MyUser user, String token) {
        tokenRepositoryMongo.save(
                Token.builder()
                        .token(token)
                        .expired(false)
                        .revoked(false)
                        .myUser(user)
                        .build()
        );
    }

    @Override
    public Map<String,String> refreshToken(String tokenRefresh) throws MyUserException, JwtException {

        if(tokenRefresh == null || !tokenRefresh.startsWith("Bearer ")) throw new JwtException("Invalid token");

        MyUser myUser = myUserRepository.findUserByEmail(jwtService.extractUserEmail(tokenRefresh))
                .orElseThrow(() -> new MyUserException("User not found."));

        List<Token> tokens = Optional.ofNullable(tokenRepositoryMongo.findByMyUser_Id(myUser.getId()))
                .orElse(Collections.emptyList());

        if(tokens.isEmpty()){
            return Map.of("Error", "Token not found.");
        }
        tokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepositoryMongo.save(token);
        });
        String access = jwtService.generateTokenAccess(myUser);
        String refresh = jwtService.generateTokenRefresh(myUser);
        saveToken(myUser,refresh);
        return Map.of("access_token", access,
                "refreshed_token",refresh);
    }

    @Override
    public MyUserResponseDto updateUserByEmail(String email, MyUserRequestDto myUserToUpdate) throws MyUserException {
        return UserMapper.entityToResponseDto(myUserRepository.updateUserByEmail(email, UserMapper.requestDtoToEntity(myUserToUpdate)).
                orElseThrow(() -> new MyUserException("Error in update record.")));
    }

    @Override
    public Boolean deleteUserByEmail(String token) throws MyUserException, JwtException {
        if(token == null || !token.startsWith("Bearer ")) throw new JwtException("Invalid token");
        String emailUser = jwtService.extractUserEmail(token);
        MyUser myUser = myUserRepository.findUserByEmail(emailUser).orElseThrow(() -> new MyUserException("User not found."));
        return myUserRepository.deleteUserByEmail(myUser.getEmail());
    }
}
