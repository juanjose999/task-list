package com.task_list.auth;

import com.task_list.exception.JwtException;
import com.task_list.exception.MyUserException;
import com.task_list.user.entity.dto.FormLogin;
import com.task_list.user.entity.dto.MyUserRequestDto;
import com.task_list.user.service.IMyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IMyUserService myUserService;


    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody MyUserRequestDto userRequestDto, BindingResult bindingResult // Captura los errores de validación
    ) throws MyUserException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Campos inválidos, por favor llena los campos correctamente");
        }
        return ResponseEntity.ok(myUserService.save(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody FormLogin formLogin, BindingResult bindingResult) throws MyUserException {
        if(bindingResult.hasErrors()) throw new IllegalArgumentException("Campos inválidos, por favor llena los campos correctamente");
        Map<String,String> result = myUserService.loginToken(formLogin);
        if(result.containsKey("access_token")) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws MyUserException, JwtException {
        if(token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Map<String,String> result = myUserService.refreshToken(token.substring(7));
        if(result.containsKey("access_token")) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

}
