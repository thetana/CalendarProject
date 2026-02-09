package com.calendarproject.user.controller;

import com.calendarproject.auth.dto.SessionUser;
import com.calendarproject.support.constants.Session;
import com.calendarproject.user.dto.DeleteUserRequest;
import com.calendarproject.user.dto.GetUserResponse;
import com.calendarproject.user.dto.UpdateUserRequest;
import com.calendarproject.user.dto.UpdateUserResponse;
import com.calendarproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    @PatchMapping("/users")
    public ResponseEntity<UpdateUserResponse> update(
            @SessionAttribute(name = Session.USER, required = false) SessionUser sessionUser,
            @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUser, request));
    }

    @DeleteMapping("/users")
    public void delete(
            @SessionAttribute(name = Session.USER, required = false) SessionUser sessionUser,
            @RequestBody DeleteUserRequest request
    ) {
        userService.delete(sessionUser, request);
    }
}
