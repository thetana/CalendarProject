package com.calendarproject.auth.controller;


import com.calendarproject.auth.dto.*;
import com.calendarproject.auth.dto.admin.AuthAdminSigninRequest;
import com.calendarproject.auth.dto.admin.AuthAdminSigninResponse;
import com.calendarproject.auth.dto.admin.AuthAdminSignupRequest;
import com.calendarproject.auth.dto.admin.AuthAdminSignupResponse;
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




    @PostMapping("/auth/admin/signup")
    public ResponseEntity<AuthAdminSignupResponse> adminSignup(@RequestBody AuthAdminSignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.adminSignup(request));
    }

    @PostMapping("/auth/admin/signin")
    public ResponseEntity<AuthAdminSigninResponse> adminSignin(@RequestBody AuthAdminSigninRequest request) {
        String token = authService.adminSignin(request);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthAdminSigninResponse(token));
    }
}