package com.task_list.user.controller;

import com.task_list.exception.JwtException;
import com.task_list.exception.MyUserException;
import com.task_list.user.entity.dto.MyUserRequestDto;
import com.task_list.user.service.IMyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MyUserController {

    private final IMyUserService myUserService;


    @GetMapping("/email")
    public ResponseEntity<?> getMyUserByEmail(@RequestParam String email) throws MyUserException {
        return ResponseEntity.ok(myUserService.findUserByEmail(email));
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody MyUserRequestDto myUser,
                                        @PathVariable String email , BindingResult bindingResult) throws MyUserException {
        if (bindingResult.hasErrors()) throw new IllegalArgumentException("Campos inválidos, por favor llena los campos correctamente");
        return ResponseEntity.ok(myUserService.updateUserByEmail(email, myUser));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws JwtException, MyUserException {
        myUserService.revokeToken(token);
        return token.isEmpty() ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() : ResponseEntity.ok().build();
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws MyUserException, JwtException {
        return myUserService.deleteUserByEmail(token) ? ResponseEntity.ok().body("User delete is successfully") : ResponseEntity.notFound().build();
    }

}
