package com.task_list.user.controller;

import com.task_list.exception.MyUserException;
import com.task_list.user.entity.MyUser;
import com.task_list.user.entity.dto.MyUserRequestDto;
import com.task_list.user.service.IMyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class MyUserController {

    private final IMyUserService myUserService;


    @GetMapping("/email")
    public ResponseEntity<?> getMyUserByEmail(@RequestParam String email) throws MyUserException {
        return ResponseEntity.ok(myUserService.findUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody MyUserRequestDto myUser) throws MyUserException {
        return ResponseEntity.ok(myUserService.save(myUser));
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<?> updateUser(@RequestBody MyUserRequestDto myUser, @PathVariable String email) throws MyUserException {
        return ResponseEntity.ok(myUserService.updateUserByEmail(email, myUser));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam String email) throws MyUserException {
        return myUserService.deleteUserByEmail(email) ? ResponseEntity.ok().body("User delete is successfully") : ResponseEntity.notFound().build();
    }

}
