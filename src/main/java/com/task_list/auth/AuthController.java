package com.task_list.auth;

import com.task_list.user.entity.dto.MyUserRequestDto;
import com.task_list.user.entity.dto.UserMapper;
import com.task_list.user.repository.IMyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IMyUserRepository myUserRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody MyUserRequestDto userRequestDto) {
        return ResponseEntity.ok(myUserRepository.save(UserMapper.requestDtoToEntity(userRequestDto)));
    }

}
