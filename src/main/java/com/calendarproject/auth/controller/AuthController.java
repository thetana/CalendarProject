package com.calendarproject.auth.controller;


import com.calendarproject.auth.dto.*;
import com.calendarproject.auth.service.AuthService;
import com.calendarproject.common.constants.Session;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthSignupResponse> signup(@RequestBody AuthSignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<AuthSigninResponse> signin(@RequestBody AuthSigninRequest request, HttpSession httpSession) {
        SessionUser sessionUser = authService.signin(request);
        httpSession.setAttribute(Session.USER, sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthSigninResponse(sessionUser.id()));
    }

    @PostMapping("/auth/signout")
    public ResponseEntity<Void> signout(@SessionAttribute(name = Session.USER, required = false) SessionUser sessionUser, HttpSession httpSession) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }

        httpSession.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}